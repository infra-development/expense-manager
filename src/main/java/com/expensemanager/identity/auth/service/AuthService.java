package com.expensemanager.identity.auth.service;

import com.expensemanager.identity.auth.dto.request.RegisterRequest;
import com.expensemanager.identity.entity.User;
import com.expensemanager.identity.exception.EmailAlreadyExistsException;
import com.expensemanager.identity.repository.UserRepository;
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

    @Transactional
    public User register(RegisterRequest request) {

        String normalizedEmail =
                request.email()
                        .trim()
                        .toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmailAndIsDeletedFalse(
                normalizedEmail)) {

            throw new EmailAlreadyExistsException(
                    normalizedEmail);
        }

        User user = new User(
                normalizedEmail,
                passwordEncoder.encode(request.password()),
                request.firstName(),
                request.lastName()
        );

        return userRepository.save(user);
    }
}