package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.exceptions.NotFoundException;
import com.endava.interns.readersnestbackendbookclubs.exceptions.NotMatchException;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Message;

public interface MessageService {

    Iterable<Message> findAllMessages();
    Iterable<Message> findMessagesFromBookClub(Long bookClubId);
    Message createMessage(Long bookClubId, Message newMessage);
    Message findMessage(Long messageId);

}
