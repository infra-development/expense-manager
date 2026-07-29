package com.expensemanager.identity.auth.service;

import com.expensemanager.identity.auth.dto.request.LoginRequest;
import com.expensemanager.identity.auth.dto.request.RefreshTokenRequest;
import com.expensemanager.identity.auth.dto.request.RegisterRequest;
import com.expensemanager.identity.auth.dto.response.LoginResponse;
import com.expensemanager.identity.entity.RefreshToken;
import com.expensemanager.identity.entity.User;
import com.expensemanager.identity.exception.EmailAlreadyExistsException;
import com.expensemanager.identity.exception.InvalidCredentialsException;
import com.expensemanager.identity.exception.InvalidRefreshTokenException;
import com.expensemanager.identity.exception.UserNotFoundException;
import com.expensemanager.identity.repository.UserRepository;
import com.expensemanager.identity.security.JwtService;
import com.expensemanager.identity.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public User register(RegisterRequest request) {

        String normalizedEmail = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmailAndIsDeletedFalse(normalizedEmail)) {
            throw new EmailAlreadyExistsException(normalizedEmail);
        }

        User user = new User(
                normalizedEmail,
                passwordEncoder.encode(request.password()),
                request.firstName(),
                request.lastName()
        );

        return userRepository.save(user);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {

        String normalizedEmail = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        User user = userRepository
                .findByEmailAndIsDeletedFalse(normalizedEmail)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(
                request.password(),
                user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtService.generateAccessToken(user.getId());

        String refreshToken = refreshTokenService.createRefreshToken(user);

        return createLoginResponse(
                accessToken,
                refreshToken
        );
    }

    @Transactional
    public LoginResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = validateRefreshToken(request.refreshToken());

        // 3. Load user
        User user = refreshToken.getUser();

        // 4. Generate new access token
        String accessToken = jwtService.generateAccessToken(user.getId());

        // 5. Revoke old refresh token
        refreshTokenService.revoke(refreshToken);

        // 6. Generate new refresh token
        String newRefreshToken = refreshTokenService.createRefreshToken(user);

        // 7. Return response
        return createLoginResponse(
                accessToken,
                newRefreshToken
        );
    }

    private LoginResponse createLoginResponse(
            String accessToken,
            String refreshToken) {

        return new LoginResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtService.getAccessTokenExpirationSeconds()
        );
    }

    @Transactional
    public void logout(RefreshTokenRequest request) {

        RefreshToken refreshToken = validateRefreshToken(request.refreshToken());

        refreshTokenService.revoke(refreshToken);
    }

    @Transactional
    public void logoutAll(UUID userId) {

        User user = userRepository
                .findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        refreshTokenService.revokeAll(user);
    }

    private RefreshToken validateRefreshToken(String rawRefreshToken) {
        // 1. Find refresh token
        RefreshToken refreshToken = refreshTokenService
                .findByRefreshToken(rawRefreshToken)
                .orElseThrow(() ->
                        new InvalidRefreshTokenException("Invalid refresh token"));

        // 2. Validate refresh token
        if (!refreshTokenService.isValid(rawRefreshToken, refreshToken)) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        return refreshToken;
    }

}