package com.endava.interns.readersnestbackendbookclubs.controllers;

import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Administrator;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.BookClub;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Member;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Message;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.AdministratorRepository;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.BookClubRepository;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.MemberRepository;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/bookclub")
public class BookClubController {

    @Autowired
    private BookClubRepository bookClubRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private MemberRepository memberRepository;

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
        LocalDateTime now = LocalDateTime.now();
        for (Message x : bookClub.getMessages()) {
            x.setPublishedDate(now);
            x.setBookClub(bookClub);
        }

        for (Administrator admin : bookClub.getAdmins()) {
            admin.setBookClub(bookClub);
        }

        for (Member member : bookClub.getMembers()) {
            member.setBookClub(bookClub);
        }
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
    @ResponseBody
    public Iterable<Message> getMessagesFromBookClub( @PathVariable(value = "bookClubId") Long bookClubId ) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        return bk.getMessages();
    }

    @GetMapping(path = "/messages/{id}")
    @ResponseBody
    public Message getMessageById( @PathVariable(value = "id") Long id ) {
        return messageRepository.findById(id).orElseThrow(() -> new NullPointerException("Message not found"));
    }

    @PostMapping(path = "/{bookClubId}/messages")
    public Message saveMessage( @PathVariable(value = "bookClubId") Long bookClubId, @RequestBody Message message) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));

        List<Member> members = bk.getMembers();
        for (int i = 0; i < members.size(); ++i) {
            Member act = members.get(i);
            if(act.getMemberId().equals(message.getUserId())) {
                LocalDateTime now = LocalDateTime.now();
                message.setPublishedDate(now);

                message.setBookClub(bk);

                bk.getMessages().add(message);
                messageRepository.save(message);
                bookClubRepository.save(bk);
                return message;
            }
        }
        return null;
    }

    @PutMapping(path = "/{bookClubId}/messages/{id}")
    public Message updateMessage( @PathVariable(value = "bookClubId") Long bookClubId, @PathVariable(value = "id") Long id, @RequestBody Message message) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        for (Message msg : bk.getMessages()) {
            if(msg.getMessageId()==id) {
                msg.setContentMessage(message.getContentMessage());
                LocalDateTime now = LocalDateTime.now();

                msg.setPublishedDate(now);

                msg.setBookClub(bk);
                bk.getMessages().add(msg);

                bookClubRepository.save(bk);
                return msg;
            }
        }
        throw new NullPointerException("Message not found");
    }

    @DeleteMapping(path = "/{bookClubId}/messages/{id}")
    public void deleteMessage( @PathVariable(value = "bookClubId") Long bookClubId, @PathVariable(value = "id") Long id ) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        List<Message> messages = bk.getMessages();
        for (int i = 0; i < messages.size(); ++i) {
            Message act = messages.get(i);
            if(act.getMessageId() == id) {
                messages.remove(i);
                bk.setMessages(messages);
                bookClubRepository.save(bk);
                break;
            }
        }
    }

    //CRUD MEMBERS

    @GetMapping(path = "/members")
    @ResponseBody
    public Iterable<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @GetMapping(path = "/{bookClubId}/members")
    @ResponseBody
    public Iterable<Member> getMembersFromBookClub( @PathVariable(value = "bookClubId") Long bookClubId ) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        return bk.getMembers();
    }

    @PostMapping(path = "/{bookClubId}/members")
    public Member addMember( @PathVariable(value = "bookClubId") Long bookClubId, @RequestBody Member member) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));

        member.setBookClub(bk);

        bk.getMembers().add(member);
        memberRepository.save(member);
        bookClubRepository.save(bk);
        return member;
    }

    @DeleteMapping(path = "/{bookClubId}/members/{id}")
    public void deleteMember( @PathVariable(value = "bookClubId") Long bookClubId, @PathVariable(value = "id") String id ) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        List<Member> members = bk.getMembers();
        for (int i = 0; i < members.size(); ++i) {
            Member act = members.get(i);
            if(act.getMemberId().equals(id)) {
                members.remove(i);
                bk.setMembers(members);
                break;
            }
        }

        List<Administrator> admins = bk.getAdmins();
        removeAdmin(id,bk,admins);
        bookClubRepository.save(bk);
    }

    //CRUD ADMINS

    @GetMapping(path = "/admins")
    @ResponseBody
    public Iterable<Administrator> getAllAdmins() {
        return administratorRepository.findAll();
    }

    @GetMapping(path = "/{bookClubId}/admins")
    @ResponseBody
    public Iterable<Administrator> getAdminsFromBookClub( @PathVariable(value = "bookClubId") Long bookClubId ) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        return bk.getAdmins();
    }

    @PostMapping(path = "/{bookClubId}/admins")
    public Administrator addAdmin( @PathVariable(value = "bookClubId") Long bookClubId, @RequestBody Administrator administrator) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));

        boolean found = false;
        List<Member> members = bk.getMembers();
        for (int i = 0; i < members.size(); ++i) {
            Member act = members.get(i);
            if(act.getMemberId().equals(administrator.getAdminId())) {

                administrator.setBookClub(bk);
                bk.getAdmins().add(administrator);

                administratorRepository.save(administrator);
                bookClubRepository.save(bk);
                found = true;
                break;
            }
        }

        if(!found) {
            Member member = new Member();
            member.setMemberId(administrator.getAdminId());
            member.setBookClub(bk);
            administrator.setBookClub(bk);

            bk.getMembers().add(member);
            bk.getAdmins().add(administrator);

            memberRepository.save(member);
            administratorRepository.save(administrator);
            bookClubRepository.save(bk);
        }

        return administrator;
    }

    @DeleteMapping(path = "/{bookClubId}/admins/{id}")
    public void deleteAdmin( @PathVariable(value = "bookClubId") Long bookClubId, @PathVariable(value = "id") String id ) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        List<Administrator> admins = bk.getAdmins();
        removeAdmin(id,bk,admins);
        bookClubRepository.save(bk);
    }

    private void removeAdmin(String id, BookClub bk, List<Administrator> admins) {
        for (int i = 0; i < admins.size(); ++i) {
            Administrator act = admins.get(i);
            if(act.getAdminId().equals(id)) {
                admins.remove(i);
                bk.setAdmins(admins);
                break;
            }
        }
    }

    //End CRUD Messages

    //@RequestParam
    //@PathVariable
    //RequestBody needs a JSON in body

}