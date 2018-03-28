package com.endava.interns.readersnestbackendbooks.persistence.repositories;

import com.endava.interns.readersnestbackendbooks.persistence.entities.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}