package com.sii.conference.exceptions.reservation;

import com.sii.conference.exceptions.themedpath.ThemedPathOperationException;

public class ReservationCollidesWithAnotherException extends ThemedPathOperationException {
    public ReservationCollidesWithAnotherException(String errorMessage) {
        super(errorMessage);
    }
}
