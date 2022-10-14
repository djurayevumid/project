package com.javamentor.qa.platform.exception;

public class UsersNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 5814055950333180669L;
    public UsersNotFoundException(String message) {
        super(message);
    }
}