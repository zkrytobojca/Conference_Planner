package com.sii.conference.exceptions.reservation;

import com.sii.conference.exceptions.themedpath.ThemedPathOperationException;

public class NoReservationWithThisIDException extends ReservationOperationException {
    public NoReservationWithThisIDException(String errorMessage) {
        super(errorMessage);
    }
}
