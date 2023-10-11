package com.sii.conference.exceptions.user;

import com.sii.conference.exceptions.themedpath.ThemedPathOperationException;

public class UserWithThisLoginExistsException extends UserOperationException {
    public UserWithThisLoginExistsException(String errorMessage) {
        super(errorMessage);
    }
}
