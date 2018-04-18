package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Message;
import org.springframework.http.ResponseEntity;

public interface MessageService {

    ResponseEntity<Iterable<Message>> findAllMessages();
    ResponseEntity<Iterable<Message>> findMessagesFromBookClub(Long bookClubId);
    ResponseEntity<Message> createMessage(Long bookClubId, Message newMessage, String userId);
    ResponseEntity<Message> findMessage(Long messageId);

}
