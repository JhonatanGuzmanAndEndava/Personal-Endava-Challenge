package com.endava.interns.readersnestbackendbooks.controllers;

import com.endava.interns.readersnestbackendbooks.exceptions.BookNotFoundException;
import com.endava.interns.readersnestbackendbooks.exceptions.CustomException;
import com.endava.interns.readersnestbackendbooks.persistence.entities.Review;
import com.endava.interns.readersnestbackendbooks.response.ResponseMessage;
import com.endava.interns.readersnestbackendbooks.security.AuthService;
import com.endava.interns.readersnestbackendbooks.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.endava.interns.readersnestbackendbooks.security.SecurityConstants.HEADER_STRING;

@RestController
@RequestMapping(path = "/books/{bookId}/reviews")
public class ReviewController {

    private ReviewService reviewService;
    private AuthService authService;

    @Autowired
    public ReviewController(ReviewService reviewService, AuthService authService) {
        this.reviewService = reviewService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseMessage<List<Review>>
    getReviewsFromBook(@PathVariable("bookId") String bookId) throws CustomException {

        Optional<List<Review>> optionalReviews = reviewService.getReviewsForBook(bookId);

        if(!optionalReviews.isPresent()) throw new BookNotFoundException();

        return new ResponseMessage<>(optionalReviews.get());
    }

    @PostMapping
    public ResponseMessage<Review>
    postReview(@RequestHeader(value = HEADER_STRING, required = false) String authHeader,
               @PathVariable("bookId") String bookId, @RequestBody Review review) throws CustomException{

        authService.checkJWT(review.getAuthorId(), authHeader);

        review.setPostedDate(LocalDate.now());
        review = reviewService.createReview(bookId, review);

        return new ResponseMessage<>(review);
    }

    @PutMapping
    public ResponseMessage<Review>
    updateReview(@RequestHeader(value = HEADER_STRING, required = false) String authHeader,
               @PathVariable("bookId") String bookId, @RequestBody Review review) throws CustomException{

        authService.checkJWT(review.getAuthorId(), authHeader);

        review = reviewService.updateReview(bookId, review);

        return new ResponseMessage<>(review);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseMessage<Review>
    deleteReview(@RequestHeader(value = HEADER_STRING, required = false) String authHeader,
                 @PathVariable("bookId") String bookId, @PathVariable("userId") String authorId) throws CustomException{

        authService.checkJWT(authorId, authHeader);

        Review review = reviewService.deleteReview(bookId, authorId);

        return new ResponseMessage<>(review);
    }
}
