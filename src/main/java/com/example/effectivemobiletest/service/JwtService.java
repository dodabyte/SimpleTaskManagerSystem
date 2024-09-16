package com.example.effectivemobiletest.service;

import com.example.effectivemobiletest.entity.User;

public interface JwtService {
    String extractEmail(String token);

    String generateToken(User user);

    boolean isTokenValid(String token, User user);
}
