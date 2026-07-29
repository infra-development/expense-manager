package com.expensemanager.identity.auth.service;

import com.expensemanager.identity.config.JwtProperties;
import com.expensemanager.identity.entity.RefreshToken;
import com.expensemanager.identity.entity.User;
import com.expensemanager.identity.repository.RefreshTokenRepository;
import com.expensemanager.identity.security.JwtService;
import com.expensemanager.identity.security.TokenHashService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final TokenHashService tokenHashService;

    /**
     * Creates a refresh token without device information.
     * Can be used until device tracking is implemented.
     */
    public String createRefreshToken(User user) {
        return createRefreshToken(user, null);
    }

    /**
     * Generates a refresh token for the user, stores only its SHA-256 hash
     * in the database, and returns the raw token to the caller.
     */
    public String createRefreshToken(User user, String deviceId) {

        String rawRefreshToken = jwtService.generateRefreshToken(user.getId());

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setDeviceId(deviceId);
        refreshToken.setTokenHash(tokenHashService.hash(rawRefreshToken));
        refreshToken.setExpiresAt(
                OffsetDateTime.now(ZoneOffset.UTC)
                        .plus(Duration.ofMillis(jwtProperties.getRefreshExpiration()))
        );
        refreshToken.setRevoked(false);

        refreshTokenRepository.save(refreshToken);
        return rawRefreshToken;
    }

    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByRefreshToken(String rawRefreshToken) {

        String tokenHash = tokenHashService.hash(rawRefreshToken);

        return refreshTokenRepository.findByTokenHash(tokenHash);
    }

    @Transactional(readOnly = true)
    public boolean isValid(String rawRefreshToken, RefreshToken refreshToken) {

        boolean revoked = refreshToken.isRevoked();
        boolean expired = refreshToken.getExpiresAt().isBefore(OffsetDateTime.now(ZoneOffset.UTC));
        boolean jwtValid = jwtService.isRefreshTokenValid(rawRefreshToken);

        return !revoked
                && !expired
                && jwtValid;
    }

    public void revoke(RefreshToken refreshToken) {

        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }

    public void revokeAll(User user) {

        List<RefreshToken> tokens =
                refreshTokenRepository.findByUserAndRevokedFalse(user);

        tokens.forEach(token -> token.setRevoked(true));

        refreshTokenRepository.saveAll(tokens);
    }

    @Transactional
    public int deleteExpiredTokens() {

        return refreshTokenRepository.deleteByExpiresAtBefore(
                OffsetDateTime.now(ZoneOffset.UTC)
        );
    }
}