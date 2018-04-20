package com.endava.interns.readersnestbackendbooks.services;

import com.endava.interns.readersnestbackendbooks.exceptions.BookNotFoundException;
import com.endava.interns.readersnestbackendbooks.exceptions.CreateReviewException;
import com.endava.interns.readersnestbackendbooks.exceptions.DeleteReviewException;
import com.endava.interns.readersnestbackendbooks.exceptions.UpdateReviewException;
import com.endava.interns.readersnestbackendbooks.persistence.entities.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Review createReview(String bookId, Review review) throws BookNotFoundException,
            CreateReviewException;
    Optional<List<Review>> getReviewsForBook(String bookId);
    Review updateReview(String bookId, Review newReview) throws BookNotFoundException,
            UpdateReviewException;
    Review deleteReview(String bookId, String authorId) throws BookNotFoundException,
            DeleteReviewException;
}
