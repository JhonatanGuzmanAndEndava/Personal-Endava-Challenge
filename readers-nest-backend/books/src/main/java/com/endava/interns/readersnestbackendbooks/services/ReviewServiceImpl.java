package com.endava.interns.readersnestbackendbooks.services;

import com.endava.interns.readersnestbackendbooks.exceptions.BookNotFoundException;
import com.endava.interns.readersnestbackendbooks.exceptions.CreateReviewException;
import com.endava.interns.readersnestbackendbooks.exceptions.DeleteReviewException;
import com.endava.interns.readersnestbackendbooks.exceptions.UpdateReviewException;
import com.endava.interns.readersnestbackendbooks.persistence.entities.Book;
import com.endava.interns.readersnestbackendbooks.persistence.entities.Review;
import com.endava.interns.readersnestbackendbooks.persistence.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private BookRepository bookRepository;

    @Autowired
    public ReviewServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Review createReview(String bookId, Review review) throws BookNotFoundException, CreateReviewException {

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(!optionalBook.isPresent()) throw new BookNotFoundException();

        Book book = optionalBook.get();
        int pos = authorHasReview(review.getAuthorId(), book.getReviews());
        if(pos != -1) throw new CreateReviewException();

        book.getReviews().add(review);
        bookRepository.save(book);

        return review;
    }

    @Override
    public Optional<List<Review>> getReviewsForBook(String bookId) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        return optionalBook.map(Book::getReviews);
    }

    @Override
    public Review updateReview(String bookId, Review updatedReview) throws BookNotFoundException, UpdateReviewException {

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(!optionalBook.isPresent()) throw new BookNotFoundException();

        List<Review> reviews = optionalBook.get().getReviews();
        int pos = authorHasReview(updatedReview.getAuthorId(), reviews);
        if(pos == -1) throw new UpdateReviewException(UpdateReviewException.NO_REVIEW);

        updatedReview = reviews.get(pos).updateFromNew(updatedReview);
        reviews.set(pos, updatedReview);
        bookRepository.save(optionalBook.get().setReviews(reviews));

        return updatedReview;
    }

    @Override
    public Review deleteReview(String bookId, String authorId) throws BookNotFoundException, DeleteReviewException {

        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if(!optionalBook.isPresent()) throw new BookNotFoundException();

        List<Review> reviews = optionalBook.get().getReviews();
        int pos = authorHasReview(authorId, reviews);

        if(pos == -1) throw new DeleteReviewException(DeleteReviewException.NO_REVIEW);

        Review deletedReview = reviews.remove(pos);

        bookRepository.save(optionalBook.get().setReviews(reviews));

        return deletedReview;
    }

    private int authorHasReview(String authorId, List<Review> reviews){

        int pos = 0;
        for(Review r: reviews){
            if(r.getAuthorId().equals(authorId)) return pos;
            pos++;
        }
        return -1;
    }
}
