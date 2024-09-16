package com.example.effectivemobiletest.exception;

public class TaskNotFoundException extends RuntimeException {
    private static final String TASK_NOT_FOUND_MESSAGE = "Указанная задача не найдена";

    public TaskNotFoundException() {
        super(TASK_NOT_FOUND_MESSAGE);
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
