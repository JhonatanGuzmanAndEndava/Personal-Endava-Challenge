package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.persistence.entities.BookClub;

public interface BookClubService {

    Iterable<BookClub> findAll();
    Iterable<BookClub> findPublicBookClubs();
    BookClub createBookClub(BookClub newBookClub);
    BookClub findBookClub(Long bookClubId);
    BookClub updateBookClub(Long bookClubId, BookClub bookClub, String adminId);
    void deleteBookClub(Long bookClubId, String adminId);
}
