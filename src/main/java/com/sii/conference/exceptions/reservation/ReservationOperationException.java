package com.sii.conference.exceptions.reservation;

public abstract class ReservationOperationException extends RuntimeException {
    public ReservationOperationException(String errorMessage) {
        super(errorMessage);
    }
}
