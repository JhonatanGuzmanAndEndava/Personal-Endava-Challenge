package com.endava.interns.readersnestbackend.books.persistence.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Review {

    private String authorId;
    private String reviewContent;
    private LocalDate postedDate;
}
