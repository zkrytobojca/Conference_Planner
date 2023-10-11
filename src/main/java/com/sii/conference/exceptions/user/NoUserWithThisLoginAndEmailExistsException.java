package com.sii.conference.exceptions.user;

import com.sii.conference.exceptions.themedpath.ThemedPathOperationException;

public class NoUserWithThisLoginAndEmailExistsException extends UserOperationException {
    public NoUserWithThisLoginAndEmailExistsException(String errorMessage) {
        super(errorMessage);
    }
}
