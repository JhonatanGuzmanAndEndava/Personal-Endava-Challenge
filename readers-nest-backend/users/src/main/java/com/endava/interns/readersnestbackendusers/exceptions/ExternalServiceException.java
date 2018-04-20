package com.endava.interns.readersnestbackendusers.exceptions;

public class ExternalServiceException extends CustomException {

    public ExternalServiceException(String error, String description) {
        super(error, description);
    }
}
