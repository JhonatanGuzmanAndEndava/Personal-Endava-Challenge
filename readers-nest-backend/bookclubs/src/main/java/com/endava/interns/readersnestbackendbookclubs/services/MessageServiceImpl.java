package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.exceptions.NotFoundException;
import com.endava.interns.readersnestbackendbookclubs.exceptions.NotMatchException;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.BookClub;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Message;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.BookClubRepository;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.MemberRepository;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageServiceImpl implements MessageService {

    private BookClubRepository bookClubRepository;
    private MessageRepository messageRepository;
    private MemberRepository memberRepository;

    @Autowired
    public MessageServiceImpl(BookClubRepository bookClubRepository, MessageRepository messageRepository, MemberRepository memberRepository) {
        this.bookClubRepository = bookClubRepository;
        this.messageRepository = messageRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Iterable<Message> findAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Iterable<Message> findMessagesFromBookClub(Long bookClubId) {
        BookClub bk = null;
        try {
            bk = bookClubRepository.findById(bookClubId).orElseThrow(
                    () -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return bk.getMessages();
    }

    @Override
    public Message createMessage(Long bookClubId, Message newMessage) {

        BookClub bk = null;
        try {
            bk = bookClubRepository.findById(bookClubId).orElseThrow(
                    () -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        try {
            memberRepository.findMemberByMemberIdAndBookClub_BookClubId(newMessage.getUserId(),bookClubId)
                    .orElseThrow(() -> new NotMatchException("Not match between Member and Bookclub", "Member is not from this Bookclub"));
        } catch (NotMatchException e) {
            e.printStackTrace();
        }

        LocalDateTime now = LocalDateTime.now();
        newMessage.setPublishedDate(now);
        newMessage.setBookClub(bk);

        bk.getMessages().add(newMessage);
        messageRepository.save(newMessage);
        bookClubRepository.save(bk);
        return newMessage;
    }

    @Override
    public Message findMessage(Long messageId) {
        try {
            return messageRepository.findById(messageId).orElseThrow(
                    () -> new NotFoundException("Message not found", "Message doesn\'t exist in database"));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
