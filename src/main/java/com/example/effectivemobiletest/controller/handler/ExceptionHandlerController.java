package com.example.effectivemobiletest.controller.handler;

import com.example.effectivemobiletest.dto.response.ErrorResponseDto;
import com.example.effectivemobiletest.exception.AuthenticationException;
import com.example.effectivemobiletest.exception.CommentNotFoundException;
import com.example.effectivemobiletest.exception.DeleteCommentAnotherUserException;
import com.example.effectivemobiletest.exception.DeleteTaskAnotherUserException;
import com.example.effectivemobiletest.exception.TaskNotFoundException;
import com.example.effectivemobiletest.exception.UpdateCommentAnotherUserException;
import com.example.effectivemobiletest.exception.UpdateTaskAnotherUserException;
import com.example.effectivemobiletest.exception.UserAlreadyRegisteredException;
import com.example.effectivemobiletest.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {
    @ExceptionHandler({
            UserNotFoundException.class,
            TaskNotFoundException.class,
            CommentNotFoundException.class
    })
    public ResponseEntity<ErrorResponseDto> handleNotFoundStatusException(Exception e, WebRequest request) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage(), HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            UserAlreadyRegisteredException.class
    })
    public ResponseEntity<ErrorResponseDto> handleBadRequestStatusException(Exception e, WebRequest request) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            AuthenticationException.class
    })
    public ResponseEntity<ErrorResponseDto> handleUnauthorizedStatusException(Exception e, WebRequest request) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage(), HttpStatus.UNAUTHORIZED, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            UpdateTaskAnotherUserException.class,
            DeleteTaskAnotherUserException.class,
            UpdateCommentAnotherUserException.class,
            DeleteCommentAnotherUserException.class
    })
    public ResponseEntity<ErrorResponseDto> handleNotAcceptableStatusException(Exception e, WebRequest request) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage(), HttpStatus.NOT_ACCEPTABLE, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        List<FieldError> errors = e.getFieldErrors();
        String message = errors.stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(", "));
        ErrorResponseDto errorResponse = new ErrorResponseDto(message, HttpStatus.BAD_REQUEST, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
