package com.example.effectivemobiletest.exception;

public class DeleteTaskAnotherUserException extends RuntimeException {
    private static final String DELETE_TASK_ANOTHER_USER_MESSAGE = "Выбранная задача не может удаляться чужим пользователем";

    public DeleteTaskAnotherUserException() {
        super(DELETE_TASK_ANOTHER_USER_MESSAGE);
    }

    public DeleteTaskAnotherUserException(String message) {
        super(message);
    }
}
