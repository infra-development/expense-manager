package com.expensemanager.identity.auth.controller;

import com.expensemanager.identity.auth.dto.request.RegisterRequest;
import com.expensemanager.identity.auth.dto.response.UserResponse;
import com.expensemanager.identity.auth.service.AuthService;
import com.expensemanager.identity.entity.User;
import com.expensemanager.identity.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {

        User user = authService.register(request);
        return userMapper.toResponse(user);
    }
}