package com.javamentor.qa.platform.exception;

public class ChatAlreadyDeletedException extends RuntimeException {
    private static final long serialVersionUID = 5814055950333180669L;

    public ChatAlreadyDeletedException(String message) {
        super(message);
    }
}
