package com.endava.interns.readersnestbackendbooks.services;

import com.endava.interns.readersnestbackendbooks.exceptions.BookNotFoundException;
import com.endava.interns.readersnestbackendbooks.exceptions.CreateReviewException;
import com.endava.interns.readersnestbackendbooks.exceptions.DeleteReviewException;
import com.endava.interns.readersnestbackendbooks.exceptions.UpdateReviewException;
import com.endava.interns.readersnestbackendbooks.persistence.entities.Book;
import com.endava.interns.readersnestbackendbooks.persistence.entities.Review;
import com.endava.interns.readersnestbackendbooks.persistence.repositories.BookRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReviewServiceImplTest {

    private ReviewServiceImpl reviewService;

    private BookRepository bookRepository;

    @Before
    public void setUp(){

        bookRepository = mock(BookRepository.class);

        reviewService = new ReviewServiceImpl(bookRepository);
    }

    @Test
    public void testAddingReviewToNonExistingBook(){

        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        try {
            Review review = reviewService.createReview("1", new Review());
            fail("An exception should have been thrown");
        } catch (BookNotFoundException e) {
            assertEquals("notFound", e.getError());
            assertEquals("That book doesn't exists", e.getDescription());
        } catch (CreateReviewException e) {
            fail("the book shouldn't exist");
        }
    }

    @Test
    public void testAddingReviewFromAlreadyExistingUser(){

        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review().setAuthorId("1"));
        Book book = new Book().setReviews(reviews);
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        try {
            Review review = reviewService.createReview("1", new Review().setAuthorId("1"));
            fail("An exception should have been thrown");
        } catch (BookNotFoundException e) {
            fail("the book should exist");
        } catch (CreateReviewException e) {
            assertEquals("createReview", e.getError());
            assertEquals("The author already has a review in the book", e.getDescription());
        }
    }

    @Test
    public void testAddingReviewFromNonExistingUser(){

        Book book = new Book().setReviews(new ArrayList<>());
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        try {
            Review review = reviewService.createReview("1", new Review().setAuthorId("1")
                                                            .setReviewContent("review"));

            assertNotNull("Review shouldn't be null", review);
            assertEquals("1", review.getAuthorId());
            assertEquals("review", review.getReviewContent());
        } catch (BookNotFoundException e) {
            fail("the book should exist");
        } catch (CreateReviewException e) {
            fail("The review should have been created");
        }
    }

    @Test
    public void testGetReviewsFromNonExistingBook() {

        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        Optional<List<Review>> optionalReviews = reviewService.getReviewsForBook("1");

        assertFalse("There should be no list", optionalReviews.isPresent());

    }

    @Test
    public void testGetReviewsFromExistingBook() {

        when(bookRepository.findById("1"))
                .thenReturn(Optional.of(new Book().setReviews(new ArrayList<>())));

        Optional<List<Review>> optionalReviews = reviewService.getReviewsForBook("1");

        assertTrue("There should be a list", optionalReviews.isPresent());

    }

    @Test
    public void testUpdateReviewInNonExistingBook() {
        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        try {
            Review review = reviewService.updateReview("1", produceReview());
            fail("An exception should have been thrown");
        } catch (BookNotFoundException ignored) {

        } catch (UpdateReviewException e) {
            fail("The book shouldn't have been found");
        }
    }

    @Test
    public void testUpdateReviewFromNonExistingUser() {

        Book book = new Book()
                .setId("1")
                .setReviews(new ArrayList<>());

        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        try {
            Review review = reviewService.updateReview("1", produceReview());
            fail("The review shouldn't be added");
        } catch (BookNotFoundException e) {
            fail("The book should exist");
        } catch (UpdateReviewException e) {
            assertEquals(UpdateReviewException.NO_REVIEW, e.getDescription());
        }
    }

    @Test
    public void testUpdateReviewFromExistingUser() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(produceReview().setAuthorId("2"));
        reviews.add(produceReview());
        Book book = new Book()
                .setId("1")
                .setReviews(reviews);

        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        try {
            Review updatedReview = reviewService.updateReview("1",
                    produceReview().setReviewContent("content2"));
            assertNotNull("It should have returned the updated review", updatedReview);
            assertEquals("content2", updatedReview.getReviewContent());

        } catch (BookNotFoundException | UpdateReviewException e) {
            fail("No exception should be thrown");
        }
    }

    @Test
    public void testDeleteNonExistingBookReview() {

        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        try {
            Review review = reviewService.deleteReview("1", "1");
            fail("Book shouldn't have been found");
        } catch (BookNotFoundException ignored) {
        } catch (DeleteReviewException e) {
            fail("Wrong exception");
        }
    }

    @Test
    public void testDeleteNonExistingReview() {
        Book book = new Book()
                .setId("1")
                .setReviews(new ArrayList<>());
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        try {
            Review review = reviewService.deleteReview("1", "1");
            fail("Non existing review should have thrown exception");
        } catch (BookNotFoundException e) {
            fail("Book should have been found");
        } catch (DeleteReviewException ignored) {
        }

    }

    @Test
    public void testDeleteReviewFromExistingUser() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(produceReview().setAuthorId("2"));
        reviews.add(produceReview());
        Book book = new Book()
                .setId("1")
                .setReviews(reviews);

        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        try {
            Review review = reviewService.deleteReview("1", "1");
            assertNotNull("Review shouldn't be null", review);
            assertEquals("1", review.getAuthorId());
            assertEquals("review", review.getReviewContent());
        } catch (BookNotFoundException | DeleteReviewException e) {
            fail("No exception should have been thrown");
        }
    }

    private Review produceReview(){
        return new Review()
                .setAuthorId("1")
                .setReviewContent("review");
    }
}