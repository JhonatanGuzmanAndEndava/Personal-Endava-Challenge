package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.persistence.entities.BookClub;

public interface BookClubService {

    Iterable<BookClub> findAll();
    BookClub createBookClub(BookClub newBookClub);
    BookClub findBookClub(Long bookClubId);
    BookClub updateBookCLub(Long bookClubId, BookClub bookClub);
    void deleteBook(Long bookClubId);
}
