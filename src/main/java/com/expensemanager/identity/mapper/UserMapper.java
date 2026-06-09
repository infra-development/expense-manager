package com.expensemanager.identity.mapper;

import com.expensemanager.identity.auth.dto.response.UserResponse;
import com.expensemanager.identity.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);
}