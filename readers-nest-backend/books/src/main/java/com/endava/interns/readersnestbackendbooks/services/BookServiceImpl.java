package com.endava.interns.readersnestbackendbooks.services;

import com.endava.interns.readersnestbackendbooks.persistence.entities.Book;
import com.endava.interns.readersnestbackendbooks.persistence.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(Book newBook) {
        return bookRepository.save(newBook);
    }

    @Override
    public Optional<Book> readBook(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> updateBook(Book updatedBook) {
        if(bookRepository.existsById(updatedBook.getId())){
            return Optional.of(bookRepository.save(updatedBook));
        }else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> deleteBook(String id) {
        if (bookRepository.existsById(id)){
            bookRepository.deleteById(id);
            return Optional.of(id);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
