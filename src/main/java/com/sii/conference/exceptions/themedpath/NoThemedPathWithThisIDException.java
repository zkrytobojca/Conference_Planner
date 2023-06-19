package com.sii.conference.exceptions.themedpath;

public class NoThemedPathWithThisIDException extends ThemedPathOperationException{
    public NoThemedPathWithThisIDException(String errorMessage) {
        super(errorMessage);
    }
}
