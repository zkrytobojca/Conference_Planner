package com.sii.conference.exceptions.lecture;

import com.sii.conference.exceptions.themedpath.ThemedPathOperationException;

public class LectureAlreadyFullException extends LectureOperationException {
    public LectureAlreadyFullException(String errorMessage) {
        super(errorMessage);
    }
}
