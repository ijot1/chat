package com.messenger.chat.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(final String message) {
        super(message);
    }
    @Override
    public String getMessage() {
        return "";
    }
}
