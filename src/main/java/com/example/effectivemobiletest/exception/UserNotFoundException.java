package com.example.effectivemobiletest.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String USER_NOT_FOUND_MESSAGE = "Указанный пользователь не найден";

    public UserNotFoundException() {
        super(USER_NOT_FOUND_MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
