package com.sii.conference.exceptions.reservation;

import com.sii.conference.exceptions.themedpath.ThemedPathOperationException;

public class ReservationAlreadyExistsException extends ReservationOperationException {
    public ReservationAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
