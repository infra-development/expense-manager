package com.expensemanager.identity.auth.service;

import com.expensemanager.identity.auth.dto.request.LoginRequest;
import com.expensemanager.identity.auth.dto.request.RegisterRequest;
import com.expensemanager.identity.auth.dto.response.LoginResponse;
import com.expensemanager.identity.config.JwtProperties;
import com.expensemanager.identity.entity.User;
import com.expensemanager.identity.exception.EmailAlreadyExistsException;
import com.expensemanager.identity.exception.InvalidCredentialsException;
import com.expensemanager.identity.repository.UserRepository;
import com.expensemanager.identity.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;

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

        return new LoginResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtService.getAccessTokenExpirationSeconds()
        );
    }
}