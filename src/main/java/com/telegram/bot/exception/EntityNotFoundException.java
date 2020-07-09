package com.telegram.bot.exception;

public class EntityNotFoundException extends RuntimeException {

    private static final String MESSAGE_ID_TEMPLATE = "%s with id=%s not found";

    public EntityNotFoundException(Class<?> entityClass, Object id) {
        super(String.format(MESSAGE_ID_TEMPLATE, entityClass.getSimpleName(), id));
    }
}
