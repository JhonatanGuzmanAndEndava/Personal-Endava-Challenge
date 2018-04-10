package com.endava.interns.readersnestbackendbooks.exceptions;

public class DeleteReviewException extends CustomException {

    public static final String NO_REVIEW = "There is no review from that user";

    public DeleteReviewException(String description) {
        super("deleteReview", description);
    }
}
