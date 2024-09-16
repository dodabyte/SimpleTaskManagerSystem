package com.example.effectivemobiletest.service.impl;

import com.example.effectivemobiletest.dto.request.CreateUserRequestDto;
import com.example.effectivemobiletest.dto.response.UserResponseDto;
import com.example.effectivemobiletest.entity.User;
import com.example.effectivemobiletest.entity.enums.Role;
import com.example.effectivemobiletest.exception.UserAlreadyRegisteredException;
import com.example.effectivemobiletest.exception.UserNotFoundException;
import com.example.effectivemobiletest.mapper.UserMapper;
import com.example.effectivemobiletest.repository.UserRepository;
import com.example.effectivemobiletest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponseDto getUserResponseDtoByUsername(String username) {
        User user = getUserByUsername(username);
        return UserMapper.INSTANCE.fromUserToUserResponseDto(user);
    }

    @Override
    public UserResponseDto getUserResponseDtoByEmail(String email) {
        User user = getUserByEmail(email);
        return UserMapper.INSTANCE.fromUserToUserResponseDto(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с указанным именем пользователя не найден не найден"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с указанной электронной почтой не найден"));
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getUserByEmail;
    }

    @Override
    public User createUser(CreateUserRequestDto dto) {
        if (userRepository.existsByUsernameOrEmail(dto.getUsername(), dto.getEmail())) {
            throw new UserAlreadyRegisteredException("Пользователь с указанным именем пользователя или электронной почтой уже зарегистрирован");
        }

        User user = createUserFromDto(dto);

        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByUsername(username);
    }

    private User createUserFromDto(CreateUserRequestDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .registrationDate(LocalDateTime.now())
                .role(Role.ROLE_USER)
                .build();
    }
}
