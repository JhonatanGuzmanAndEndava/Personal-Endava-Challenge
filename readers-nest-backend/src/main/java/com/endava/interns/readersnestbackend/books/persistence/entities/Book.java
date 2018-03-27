package com.endava.interns.readersnestbackend.books.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "books")
public class Book {

    private String id;
    private String name;
    private String author;
    private String isbn10;
    private String isbn13;
    private String editorial;
    private LocalDate publishingDate;
    private String coverURL;
    private List<Review> reviews;
}
