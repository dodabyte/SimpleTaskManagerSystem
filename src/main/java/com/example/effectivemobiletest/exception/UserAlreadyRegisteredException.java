package com.example.effectivemobiletest.exception;

public class UserAlreadyRegisteredException extends RuntimeException {
    private static final String USER_ALREADY_EXIST_MESSAGE = "Указанный пользователь уже существует";

    public UserAlreadyRegisteredException() {
        super(USER_ALREADY_EXIST_MESSAGE);
    }

    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
