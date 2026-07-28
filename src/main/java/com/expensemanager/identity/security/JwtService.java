package com.expensemanager.identity.security;

import com.expensemanager.identity.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;
    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;

        this.accessSecretKey = Keys.hmacShaKeyFor(
                jwtProperties.getAccessSecret().getBytes(StandardCharsets.UTF_8));

        this.refreshSecretKey = Keys.hmacShaKeyFor(
                jwtProperties.getRefreshSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UUID userId) {

        Instant now = Instant.now();

        Instant expiry = now.plusMillis(jwtProperties.getAccessExpiration());

        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(accessSecretKey)
                .compact();
    }

    public String generateRefreshToken(UUID userId) {

        Instant now = Instant.now();

        Instant expiry = now.plusMillis(jwtProperties.getRefreshExpiration());

        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(refreshSecretKey)
                .compact();
    }

    public UUID extractAccessTokenUserId(String token) {
        return UUID.fromString(parseAccessToken(token).getSubject());
    }

    public UUID extractRefreshTokenUserId(String token) {
        return UUID.fromString(parseRefreshToken(token).getSubject());
    }

    public boolean isAccessTokenValid(String token) {
        try {
            parseAccessToken(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isRefreshTokenValid(String token) {
        try {
            parseRefreshToken(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private Claims parseAccessToken(String token) {
        return Jwts.parser()
                .verifyWith(accessSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Claims parseRefreshToken(String token) {
        return Jwts.parser()
                .verifyWith(refreshSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public long getAccessTokenExpirationSeconds() {
        return jwtProperties.getAccessExpiration() / 1000;
    }
}