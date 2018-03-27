package com.endava.interns.readersnestbackend.books.services;

import com.endava.interns.readersnestbackend.books.persistence.entities.Book;
import com.endava.interns.readersnestbackend.books.persistence.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book createBook(Book newBook) {
        return bookRepository.save(newBook);
    }

    @Override
    public Optional<Book> readBook(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book updateBook(Book updatedBook) {
        return bookRepository.save(updatedBook);
    }

    @Override
    public String deleteBook(String id) {
        bookRepository.deleteById(id);
        return id;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
