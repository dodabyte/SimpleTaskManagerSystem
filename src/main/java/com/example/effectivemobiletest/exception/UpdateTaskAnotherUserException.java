package com.example.effectivemobiletest.exception;

public class UpdateTaskAnotherUserException extends RuntimeException {
    private static final String UPDATE_TASK_ANOTHER_USER_MESSAGE = "Выбранная задача не может редактироваться чужим пользователем";

    public UpdateTaskAnotherUserException() {
        super(UPDATE_TASK_ANOTHER_USER_MESSAGE);
    }

    public UpdateTaskAnotherUserException(String message) {
        super(message);
    }
}
