package com.learning.todo.todo.configuration;

import com.learning.todo.todo.dto.TodoErrorResponseDto;
import com.learning.todo.todo.exceptions.TodoByIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public TodoErrorResponseDto handleSecurityException(TodoByIdNotFoundException exception) {
        return TodoErrorResponseDto
                .builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
