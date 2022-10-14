package com.javamentor.qa.platform.exception;

public class ChatBadRequestException extends RuntimeException {
    private static final long serialVersionUID = 2853220853752433399L;

    public ChatBadRequestException(String message) {
        super(message);
    }
}
