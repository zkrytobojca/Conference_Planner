package com.sii.conference.exceptions.user;

public abstract class UserOperationException extends RuntimeException {
    public UserOperationException(String errorMessage) {
        super(errorMessage);
    }
}
