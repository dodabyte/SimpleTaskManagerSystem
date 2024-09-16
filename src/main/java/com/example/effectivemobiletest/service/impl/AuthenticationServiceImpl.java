package com.example.effectivemobiletest.service.impl;

import com.example.effectivemobiletest.dto.request.CreateUserRequestDto;
import com.example.effectivemobiletest.dto.request.SignInRequestDto;
import com.example.effectivemobiletest.dto.request.SignUpRequestDto;
import com.example.effectivemobiletest.dto.response.JwtAuthenticationResponseDto;
import com.example.effectivemobiletest.entity.User;
import com.example.effectivemobiletest.service.AuthenticationService;
import com.example.effectivemobiletest.service.JwtService;
import com.example.effectivemobiletest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponseDto signUp(SignUpRequestDto request) {
        CreateUserRequestDto dto = CreateUserRequestDto.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User createdUser = userService.createUser(dto);

        String jwt = jwtService.generateToken(createdUser);

        return JwtAuthenticationResponseDto.builder()
                .token(jwt)
                .build();
    }

    @Override
    public JwtAuthenticationResponseDto signIn(SignInRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        User user = userService.getUserByEmail(request.getEmail());

        String jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponseDto.builder()
                .token(jwt)
                .build();
    }
}
