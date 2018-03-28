package com.endava.interns.readersnestbackendbooks.persistence.entities;

import lombok.Data;
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
