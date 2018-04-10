package com.endava.interns.readersnestbackendbooks.exceptions;

public class UpdateReviewException extends CustomException{

    public static final String NO_REVIEW = "There is no review from that user";

    public UpdateReviewException(String description) {
        super("updateReview", description);
    }
}
