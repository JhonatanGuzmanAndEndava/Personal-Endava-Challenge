package com.endava.interns.readersnestbackendbooks.exceptions;

public class CreateReviewException extends CustomException {

    public CreateReviewException() {
        super("createReview", "The author already has a review in the book");
    }
}
