package com.endava.interns.readersnestbackendbooks.persistence.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class Review {

    private String authorId;
    private String reviewContent;
    private LocalDate postedDate;

    public Review updateFromNew(Review changedReview){

        if(changedReview.getAuthorId() != null) authorId = changedReview.getAuthorId();
        if(changedReview.getReviewContent() != null) reviewContent = changedReview.getReviewContent();
        if(changedReview.getPostedDate() != null) postedDate = changedReview.getPostedDate();

        return this;
    }
}
