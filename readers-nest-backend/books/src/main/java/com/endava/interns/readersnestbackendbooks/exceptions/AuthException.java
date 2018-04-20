package com.endava.interns.readersnestbackendbooks.exceptions;

public class AuthException extends CustomException {

    public AuthException(String error, String description) {
        super(error, description);
    }
}
