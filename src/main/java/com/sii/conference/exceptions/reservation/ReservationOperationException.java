package com.sii.conference.exceptions.reservation;

public abstract class ReservationOperationException extends Exception{
    public ReservationOperationException(String errorMessage) {
        super(errorMessage);
    }
}
