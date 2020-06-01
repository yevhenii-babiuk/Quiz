package com.qucat.quiz.exception;

public class MailAlreadyExistsException extends RuntimeException {
    public MailAlreadyExistsException() {
    }

    public MailAlreadyExistsException(String message) {
        super(message);
    }

    public MailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
