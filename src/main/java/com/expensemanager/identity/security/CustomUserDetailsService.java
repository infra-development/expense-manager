package com.expensemanager.identity.security;

import com.expensemanager.identity.entity.User;
import com.expensemanager.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByEmailAndIsDeletedFalse(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username));

        return createUserPrincipal(user);
    }

    public UserPrincipal loadUserById(UUID userId) {

        User user = userRepository
                .findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + userId));

        return createUserPrincipal(user);
    }

    private UserPrincipal createUserPrincipal(User user) {
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPasswordHash()
        );
    }
}