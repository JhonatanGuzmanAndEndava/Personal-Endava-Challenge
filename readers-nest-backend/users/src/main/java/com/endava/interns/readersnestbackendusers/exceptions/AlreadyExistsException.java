package com.endava.interns.readersnestbackendusers.exceptions;

public class AlreadyExistsException extends CustomException {
    public AlreadyExistsException() {
        super("bookAlreadyExists", "The book already exists in the list");
    }
}
