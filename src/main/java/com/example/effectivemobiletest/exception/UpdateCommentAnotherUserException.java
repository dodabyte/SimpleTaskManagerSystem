package com.example.effectivemobiletest.exception;

public class UpdateCommentAnotherUserException extends RuntimeException {
    private static final String UPDATE_COMMENT_ANOTHER_USER_MESSAGE = "Выбранный комментарий не может редактироваться чужим пользователем";

    public UpdateCommentAnotherUserException() {
        super(UPDATE_COMMENT_ANOTHER_USER_MESSAGE);
    }

    public UpdateCommentAnotherUserException(String message) {
        super(message);
    }
}
