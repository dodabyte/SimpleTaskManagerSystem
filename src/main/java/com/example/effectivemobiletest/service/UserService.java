package com.example.effectivemobiletest.service;

import com.example.effectivemobiletest.dto.request.CreateUserRequestDto;
import com.example.effectivemobiletest.dto.response.UserResponseDto;
import com.example.effectivemobiletest.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserResponseDto getUserResponseDtoByUsername(String username);

    UserResponseDto getUserResponseDtoByEmail(String email);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User createUser(CreateUserRequestDto dto);

    User getCurrentUser();

    UserDetailsService userDetailsService();
}
