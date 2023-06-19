package com.sii.conference.exceptions.lecture;

public abstract class LectureOperationException extends Exception{
    public LectureOperationException(String errorMessage) {
        super(errorMessage);
    }
}
