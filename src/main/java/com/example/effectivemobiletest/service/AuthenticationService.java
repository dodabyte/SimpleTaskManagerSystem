package com.example.effectivemobiletest.service;

import com.example.effectivemobiletest.dto.request.SignInRequestDto;
import com.example.effectivemobiletest.dto.request.SignUpRequestDto;
import com.example.effectivemobiletest.dto.response.JwtAuthenticationResponseDto;

public interface AuthenticationService {
    JwtAuthenticationResponseDto signUp(SignUpRequestDto request);

    JwtAuthenticationResponseDto signIn(SignInRequestDto request);
}
