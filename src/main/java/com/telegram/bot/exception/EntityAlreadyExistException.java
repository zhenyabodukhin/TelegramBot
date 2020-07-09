package com.telegram.bot.exception;

public class EntityAlreadyExistException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "%s with name=%s already exist";

    public EntityAlreadyExistException(Class<?> entityClass, Object name) {
        super(String.format(MESSAGE_TEMPLATE, entityClass.getSimpleName(), name));
    }
}
