package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.exceptions.CustomException;
import com.endava.interns.readersnestbackendbookclubs.exceptions.NotFoundException;
import com.endava.interns.readersnestbackendbookclubs.exceptions.NotMatchException;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.BookClub;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.AdministratorRepository;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.BookClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookClubServiceImpl implements BookClubService {

    private BookClubRepository bookClubRepository;
    private AdministratorRepository administratorRepository;

    @Autowired
    public BookClubServiceImpl(BookClubRepository bookClubRepository, AdministratorRepository administratorRepository) {
        this.bookClubRepository = bookClubRepository;
        this.administratorRepository = administratorRepository;
    }

    @Override
    public ResponseEntity<Iterable<BookClub>> findAll() {
        return new ResponseEntity<>(bookClubRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Iterable<BookClub>> findPublicBookClubs() {
        return new ResponseEntity<>(bookClubRepository.findBookClubByIsPrivateFalse(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BookClub> createBookClub(BookClub newBookClub) {
        newBookClub.setMessages(new ArrayList<>());
        return new ResponseEntity<>(bookClubRepository.save(newBookClub), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BookClub> findBookClub(Long bookClubId) {
        try {
            BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(
                    () -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
            return new ResponseEntity<>(bk, HttpStatus.OK);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<BookClub> updateBookClub(Long bookClubId, BookClub bookClub, String adminId) {
        BookClub bk;
        try {
            bk = bookClubRepository.findById(bookClubId).orElseThrow(
                    () -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
            administratorRepository.findAdministratorByAdminIdAndBookClub_Id(adminId, bk.getId()).orElseThrow(
                    () -> new NotMatchException("Not match between Admin and Bookclub", "This id is not admin from this Bookclub"));

            bk.setName(bookClub.getName());
            bk.setDescription(bookClub.getDescription());
            bk.setActualBookId(bookClub.getActualBookId());
            bk.setIsPrivate(bookClub.getIsPrivate());
            return new ResponseEntity<>(bookClubRepository.save(bk), HttpStatus.OK);

        } catch (CustomException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Void> deleteBookClub(Long bookClubId, String adminId) {
        try {
            BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(
                    () -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
            administratorRepository.findAdministratorByAdminIdAndBookClub_Id(adminId, bk.getId()).orElseThrow(
                    () -> new NotMatchException("Not match between Admin and Bookclub", "This id is not admin from this Bookclub"));
            bookClubRepository.delete(bk);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
