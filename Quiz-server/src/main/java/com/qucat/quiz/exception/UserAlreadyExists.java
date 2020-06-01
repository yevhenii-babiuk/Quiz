package com.qucat.quiz.exception;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists() {
    }

    public UserAlreadyExists(String message) {
        super(message);
    }

    public UserAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
}
