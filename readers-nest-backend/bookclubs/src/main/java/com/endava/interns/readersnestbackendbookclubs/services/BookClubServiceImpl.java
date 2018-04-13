package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.exceptions.CustomException;
import com.endava.interns.readersnestbackendbookclubs.exceptions.NotFoundException;
import com.endava.interns.readersnestbackendbookclubs.exceptions.NotMatchException;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Administrator;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.BookClub;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.AdministratorRepository;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.BookClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Iterable<BookClub> findAll() {
        return bookClubRepository.findAll();
    }

    @Override
    public Iterable<BookClub> findPublicBookClubs() {
        return bookClubRepository.findBookClubByIsPrivateFalse();
    }

    @Override
    public BookClub createBookClub(BookClub newBookClub) {
        newBookClub.setMessages(new ArrayList<>());
        return bookClubRepository.save(newBookClub);
    }

    @Override
    public BookClub findBookClub(Long bookClubId) {
        try {
            return bookClubRepository.findById(bookClubId).orElseThrow(
                    () -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BookClub updateBookClub(Long bookClubId, BookClub bookClub, String adminId) {
        BookClub bk;
        try {
            bk = bookClubRepository.findById(bookClubId).orElseThrow(
                    () -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
            administratorRepository.findAdministratorByAdminIdAndBookClub_BookClubId(adminId, bk.getBookClubId()).orElseThrow(
                    () -> new NotMatchException("Not match between Admin and Bookclub", "This id is not admin from this Bookclub"));

            bk.setName(bookClub.getName());
            bk.setDescription(bookClub.getDescription());
            bk.setActualBookId(bookClub.getActualBookId());
            bk.setIsPrivate(bookClub.getIsPrivate());
            return bookClubRepository.save(bk);

        } catch (CustomException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteBookClub(Long bookClubId, String adminId) {
        try {
            BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(
                    () -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
            administratorRepository.findAdministratorByAdminIdAndBookClub_BookClubId(adminId, bk.getBookClubId()).orElseThrow(
                    () -> new NotMatchException("Not match between Admin and Bookclub", "This id is not admin from this Bookclub"));
            bookClubRepository.delete(bk);
        } catch (CustomException e) {
            e.printStackTrace();
        }


    }
}
