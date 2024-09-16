package com.example.effectivemobiletest.exception;

public class CommentNotFoundException extends RuntimeException {
    private static final String COMMENT_NOT_FOUND_MESSAGE = "Указанный комментарий не найден";

    public CommentNotFoundException() {
        super(COMMENT_NOT_FOUND_MESSAGE);
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}
