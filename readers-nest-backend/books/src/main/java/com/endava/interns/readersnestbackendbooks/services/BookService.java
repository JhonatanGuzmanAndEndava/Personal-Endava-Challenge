package com.endava.interns.readersnestbackendbooks.services;

import com.endava.interns.readersnestbackendbooks.persistence.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book createBook(Book newBook);
    Optional<Book> readBook(String id);
    Optional<Book> updateBook(Book updatedBook);
    Optional<String> deleteBook(String id);
    List<Book> findAll();

}
