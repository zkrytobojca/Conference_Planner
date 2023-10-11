package com.sii.conference.exceptions.themedpath;

public abstract class ThemedPathOperationException extends RuntimeException {
    public ThemedPathOperationException(String errorMessage) {
        super(errorMessage);
    }
}
