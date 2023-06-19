package com.sii.conference.exceptions.user;

import com.sii.conference.exceptions.themedpath.ThemedPathOperationException;

public class NoUserWithThisIDException extends ThemedPathOperationException {
    public NoUserWithThisIDException(String errorMessage) {
        super(errorMessage);
    }
}
