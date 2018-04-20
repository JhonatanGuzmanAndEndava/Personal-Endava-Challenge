package com.endava.interns.readersnestbackendbooks.exceptions;

public class BookNotFoundException extends CustomException {

    public BookNotFoundException() {
        super("notFound", "That book doesn't exists");
    }
}
