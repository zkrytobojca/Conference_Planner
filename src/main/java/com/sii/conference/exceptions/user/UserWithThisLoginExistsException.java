package com.sii.conference.exceptions.user;

import com.sii.conference.exceptions.themedpath.ThemedPathOperationException;

public class UserWithThisLoginExistsException extends ThemedPathOperationException {
    public UserWithThisLoginExistsException(String errorMessage) {
        super(errorMessage);
    }
}
