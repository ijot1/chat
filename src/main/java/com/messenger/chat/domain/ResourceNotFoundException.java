package com.messenger.chat.domain;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(final String message) {
        super(message);
    }
    @Override
    public String getMessage() {
        return "";
    }
}
