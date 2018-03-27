package com.endava.interns.readersnestbackend.books.persistence.repositories;

import com.endava.interns.readersnestbackend.books.persistence.entities.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
