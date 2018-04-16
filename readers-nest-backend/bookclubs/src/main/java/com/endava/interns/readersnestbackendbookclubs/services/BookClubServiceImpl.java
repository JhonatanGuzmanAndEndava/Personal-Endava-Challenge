package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.exceptions.NotFoundException;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.BookClub;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.BookClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookClubServiceImpl implements BookClubService {

    private BookClubRepository bookClubRepository;

    @Autowired
    public BookClubServiceImpl(BookClubRepository bookClubRepository) {
        this.bookClubRepository = bookClubRepository;
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
        newBookClub.setAdmins(new ArrayList<>());
        newBookClub.setMembers(new ArrayList<>());
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
    public BookClub updateBookCLub(Long bookClubId, BookClub bookClub) {
        BookClub bk = null;
        try {
            bk = bookClubRepository.findById(bookClubId).orElseThrow(
                    () -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        bk.setName(bookClub.getName());
        bk.setDescription(bookClub.getDescription());
        bk.setActualBookId(bookClub.getActualBookId());
        bk.setIsPrivate(bookClub.getIsPrivate());

        return bookClubRepository.save(bk);
    }

    @Override
    public void deleteBook(Long bookClubId) {
        try {
            BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(
                    () -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
            bookClubRepository.delete(bk);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
