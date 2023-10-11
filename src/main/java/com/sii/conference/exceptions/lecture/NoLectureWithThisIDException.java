package com.sii.conference.exceptions.lecture;

import com.sii.conference.exceptions.themedpath.ThemedPathOperationException;

public class NoLectureWithThisIDException extends LectureOperationException {
    public NoLectureWithThisIDException(String errorMessage) {
        super(errorMessage);
    }
}
