package com.sii.conference.exceptions.user;

public abstract class UserOperationException extends Exception{
    public UserOperationException(String errorMessage) {
        super(errorMessage);
    }
}
