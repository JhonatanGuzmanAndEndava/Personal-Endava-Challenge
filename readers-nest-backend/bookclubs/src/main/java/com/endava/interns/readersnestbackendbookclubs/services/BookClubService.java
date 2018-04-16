package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.persistence.entities.BookClub;
import org.springframework.http.ResponseEntity;

public interface BookClubService {

    ResponseEntity<Iterable<BookClub>> findAll();
    ResponseEntity<Iterable<BookClub>> findPublicBookClubs();
    ResponseEntity<BookClub> createBookClub(BookClub newBookClub);
    ResponseEntity<BookClub> findBookClub(Long bookClubId);
    ResponseEntity<BookClub> updateBookClub(Long bookClubId, BookClub bookClub, String adminId);
    ResponseEntity<Void> deleteBookClub(Long bookClubId, String adminId);
}
