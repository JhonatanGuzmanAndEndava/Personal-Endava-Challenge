package com.endava.interns.readersnestbackendusers.exceptions;

public class UserNotFoundException extends CustomException{

    public UserNotFoundException(String error, String description) {
        super(error, description);
    }
}
