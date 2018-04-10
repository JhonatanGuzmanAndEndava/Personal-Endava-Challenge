package com.endava.interns.readersnestbackendbooks.persistence.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "books")
@Accessors(chain = true)
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

    public Book updateFromNew(Book changedBook){

        if(changedBook.getAuthor() != null) author = (changedBook.getAuthor());
        if(changedBook.getName() != null) name = changedBook.getName();
        if(changedBook.getIsbn10() != null) isbn10 = (changedBook.getIsbn10());
        if(changedBook.getIsbn13() != null) isbn13 = (changedBook.getIsbn13());
        if(changedBook.getEditorial() != null) editorial = (changedBook.getEditorial());
        if(changedBook.getPublishingDate() != null) publishingDate = (changedBook.getPublishingDate());
        if(changedBook.getCoverURL() != null) coverURL = (changedBook.getCoverURL());

        if(changedBook.getReviews() != null) reviews = (changedBook.getReviews());

        return this;
    }
}
