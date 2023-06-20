package com.sii.conference.exceptions.reservation;

import com.sii.conference.exceptions.themedpath.ThemedPathOperationException;

public class ReservationAlreadyExistsException extends ThemedPathOperationException {
    public ReservationAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
