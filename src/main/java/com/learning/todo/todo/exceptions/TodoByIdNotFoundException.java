package com.learning.todo.todo.exceptions;

public class TodoByIdNotFoundException extends RuntimeException {

    public TodoByIdNotFoundException(String message) {
        super(message);
    }

    public TodoByIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TodoByIdNotFoundException(Throwable cause) {
        super(cause);
    }
}
