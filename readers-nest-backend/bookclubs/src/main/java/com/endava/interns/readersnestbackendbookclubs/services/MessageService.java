package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Message;

public interface MessageService {

    Iterable<Message> findAllMessages();
    Iterable<Message> findMessagesFromBookClub(Long bookClubId);
    Message createMessage(Long bookClubId, Message newMessage, String userId);
    Message findMessage(Long messageId);

}
