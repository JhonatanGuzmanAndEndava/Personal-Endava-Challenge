package com.endava.interns.readersnestbackend.books.services;

import com.endava.interns.readersnestbackend.books.persistence.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book createBook(Book newBook);
    Optional<Book> readBook(String id);
    Book updateBook(Book updatedBook);
    String deleteBook(String id);
    List<Book> findAll();

}
