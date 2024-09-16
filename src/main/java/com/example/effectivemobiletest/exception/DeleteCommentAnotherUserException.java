package com.example.effectivemobiletest.exception;

public class DeleteCommentAnotherUserException extends RuntimeException {
    private static final String DELETE_COMMENT_ANOTHER_USER_MESSAGE = "Выбранный комментарий не может удаляться чужим пользователем";

    public DeleteCommentAnotherUserException() {
        super(DELETE_COMMENT_ANOTHER_USER_MESSAGE);
    }

    public DeleteCommentAnotherUserException(String message) {
        super(message);
    }
}
