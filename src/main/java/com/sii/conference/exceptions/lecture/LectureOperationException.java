package com.sii.conference.exceptions.lecture;

public abstract class LectureOperationException extends RuntimeException {
    public LectureOperationException(String errorMessage) {
        super(errorMessage);
    }
}
