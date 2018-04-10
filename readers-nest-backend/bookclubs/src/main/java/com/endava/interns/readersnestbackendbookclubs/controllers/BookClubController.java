package com.endava.interns.readersnestbackendbookclubs.controllers;

import com.endava.interns.readersnestbackendbookclubs.persistence.entities.BookClub;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Message;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.BookClubRepository;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.MessageRepository;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/bookclub")
public class BookClubController {

    @Autowired
    private BookClubRepository bookClubRepository;

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    @ResponseBody
    public Iterable<BookClub> getAllBookClubs() {
        return bookClubRepository.findAll();
    }

    @GetMapping(path = "/{bookClubId}")
    @ResponseBody
    public BookClub getBookClubById(@PathVariable("bookClubId") Long id) {
        return bookClubRepository.findById(id).orElseThrow(() -> new NullPointerException("BookClub not found"));
    }

    @PostMapping
    public BookClub saveBookClub(@RequestBody BookClub bookClub) {
        for (Message x : bookClub.getMessages())
            x.setBookClub(bookClub);
        return bookClubRepository.save(bookClub);
    }

    @PutMapping(path = "/{id}")
    public BookClub updateBookClub(@PathVariable Long id, @RequestBody BookClub bookClub) {
        BookClub bk = bookClubRepository.findById(id).orElseThrow(() -> new NullPointerException("BookClub not found"));

        bk.setName(bookClub.getName());
        bk.setDescription(bookClub.getDescription());
        bk.setActualBookId(bookClub.getActualBookId());

        return bookClubRepository.save(bk);
    }

    @DeleteMapping("/{id}")
    public void deleteBookClub(@PathVariable(value = "id") Long bookClubId) {
        BookClub bk = bookClubRepository.findById(bookClubId)
                .orElseThrow(() -> new NullPointerException("BookClub not found"));

        bookClubRepository.delete(bk);
    }

    //CRUD Messages by BookClubID

    @GetMapping(path = "/messages")
    @ResponseBody
    public Iterable<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @GetMapping(path = "/{bookClubId}/messages")
    public Iterable<Message> getMessagesFromBookClub( @PathVariable(value = "bookClubId") Long bookClubId ) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        return bk.getMessages();
    }

    @GetMapping(path = "/messages/{id}")
    public Message getMessage( @PathVariable(value = "id") Long id ) {
        return messageRepository.findById(id).orElseThrow(() -> new NullPointerException("Message not found"));
    }

    @PostMapping(path = "/{bookClubId}/messages")
    public Message saveMessage( @PathVariable(value = "bookClubId") Long bookClubId, @RequestBody Message message) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        message.setBookClub(bk);
        bk.getMessages().add(message);
        messageRepository.save(message);
        bookClubRepository.save(bk);
        return message;
    }

    @PutMapping(path = "/{bookClubId}/messages/{id}")
    public Message updateMessage( @PathVariable(value = "bookClubId") Long bookClubId, @PathVariable(value = "id") Long id, @RequestBody Message message) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        for (Message msg : bk.getMessages()) {
            if(msg.getMessageID()==id) {
                msg.setContentMessage(message.getContentMessage());

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                msg.setPublishedDate(dtf.format(now));

                msg.setBookClub(bk);
                bk.getMessages().add(msg);

                bookClubRepository.save(bk);
                return msg;
            }
        }
        throw new NullPointerException("Message not found");
    }

    @DeleteMapping(path = "/{bookClubId}/messages/{id}")
    public void deleteMessage( @PathVariable(value = "bookClubId") Long bookClubId, @PathVariable(value = "id") long id ) {
        System.out.println("El pasado es: "+id);
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        List<Message> messages = bk.getMessages();
        for (int i = 0; i < messages.size(); ++i) {
            Message act = messages.get(i);
            System.out.println(" "+act.getMessageID()+" "+act.getContentMessage());
            if(act.getMessageID() == id) {
                System.out.println("Si estoy entrando");
                messages.remove(i);
                bk.setMessages(messages);
                bookClubRepository.save(bk);
                break;
            }
        }
    }

    //End CRUD Messages

    //@RequestParam
    //@PathVariable
    //RequestBody needs a JSON in body

}