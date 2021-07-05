package com.messenger.chat.domain;


import java.util.function.Supplier;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class aClass, Long entityId) {
        super("entity" + aClass.getSimpleName() + " with Id " + entityId + " not found");
    }
}
