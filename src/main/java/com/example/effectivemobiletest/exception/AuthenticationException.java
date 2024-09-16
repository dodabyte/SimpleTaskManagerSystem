package com.example.effectivemobiletest.exception;

public class AuthenticationException extends RuntimeException {
    private static final String AUTHENTICATION_MESSAGE = "Вы не авторизированы. Пожалуйста, пройдите регистрацию или авторизацию.";

    public AuthenticationException() {
        super(AUTHENTICATION_MESSAGE);
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
