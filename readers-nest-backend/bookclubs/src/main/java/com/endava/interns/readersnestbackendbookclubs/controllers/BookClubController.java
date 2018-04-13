package com.endava.interns.readersnestbackendbookclubs.controllers;

import com.endava.interns.readersnestbackendbookclubs.exceptions.DuplicatedException;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Administrator;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.BookClub;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Member;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Message;
import com.endava.interns.readersnestbackendbookclubs.services.AdministratorService;
import com.endava.interns.readersnestbackendbookclubs.services.BookClubService;
import com.endava.interns.readersnestbackendbookclubs.services.MemberService;
import com.endava.interns.readersnestbackendbookclubs.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/bookclub")
public class BookClubController {

    private BookClubService bookClubService;
    private MessageService messageService;
    private AdministratorService administratorService;
    private MemberService memberService;

    @Autowired
    public BookClubController(BookClubService bookClubService,
            MessageService messageService,
            AdministratorService administratorService,
            MemberService memberService)
    {
        this.bookClubService = bookClubService;
        this.messageService = messageService;
        this.administratorService = administratorService;
        this.memberService = memberService;
    }

    //CRUD BookClub

    @GetMapping
    @ResponseBody
    public Iterable<BookClub> getAllBookClubs() {
        return bookClubService.findAll();
    }

    @GetMapping(path = "/public")
    @ResponseBody
    public Iterable<BookClub> getPublicBookClubs() {
        return bookClubService.findPublicBookClubs();
    }

    @GetMapping(path = "/{bookClubId}")
    @ResponseBody
    public BookClub getBookClubById(@PathVariable("bookClubId") Long id) {
        return bookClubService.findBookClub(id);
    }

    @PostMapping
    public BookClub saveBookClub(@RequestBody BookClub bookClub) {
        return bookClubService.createBookClub(bookClub);
    }

    @PutMapping(path = "/{id}")
    public BookClub updateBookClub(@PathVariable Long id, @RequestBody BookClub bookClub) {
        return bookClubService.updateBookCLub(id, bookClub);
    }

    @DeleteMapping("/{id}")
    public void deleteBookClub(@PathVariable(value = "id") Long bookClubId) {
        bookClubService.deleteBook(bookClubId);
    }

    //CRUD Messages

    @GetMapping(path = "/messages")
    @ResponseBody
    public Iterable<Message> getAllMessages() {
        return messageService.findAllMessages();
    }

    @GetMapping(path = "/{bookClubId}/messages")
    @ResponseBody
    public Iterable<Message> getMessagesFromBookClub( @PathVariable(value = "bookClubId") Long bookClubId ) {
        return messageService.findMessagesFromBookClub(bookClubId);
    }

    @GetMapping(path = "/messages/{id}")
    @ResponseBody
    public Message getMessageById( @PathVariable(value = "id") Long id ) {
        return messageService.findMessage(id);
    }

    @PostMapping(path = "/{bookClubId}/messages")
    public Message saveMessage( @PathVariable(value = "bookClubId") Long bookClubId, @RequestBody Message message) {
        return messageService.createMessage(bookClubId, message);
    }

    //CRUD MEMBERS

    @GetMapping(path = "/{bookClubId}/members")
    @ResponseBody
    public Iterable<Member> getMembersFromBookClub( @PathVariable(value = "bookClubId") Long bookClubId ) {
        return memberService.getMembersFromBookClub(bookClubId);
    }

    @PostMapping(path = "/{bookClubId}/members")
    public Member addMember( @PathVariable(value = "bookClubId") Long bookClubId, @RequestBody Member member) throws DuplicatedException {
        return memberService.addMemberToBookClub(bookClubId, member);
    }

    @DeleteMapping(path = "/{bookClubId}/members/{id}")
    public void deleteMember( @PathVariable(value = "bookClubId") Long bookClubId, @PathVariable(value = "id") String id ) {
        memberService.deleteMemberFromBookClub(bookClubId, id);
    }

    //CRUD ADMINS

    @GetMapping(path = "/{bookClubId}/admins")
    @ResponseBody
    public Iterable<Administrator> getAdminsFromBookClub( @PathVariable(value = "bookClubId") Long bookClubId ) {
        return administratorService.getAdminsFromBookClub(bookClubId);
    }

    @PostMapping(path = "/{bookClubId}/admins")
    public Administrator addAdmin( @PathVariable(value = "bookClubId") Long bookClubId, @RequestBody Administrator administrator) {
        return administratorService.addAdminToBookClub(bookClubId, administrator);
    }

    @DeleteMapping(path = "/{bookClubId}/admins/{id}")
    public void deleteAdmin( @PathVariable(value = "bookClubId") Long bookClubId, @PathVariable(value = "id") String id ) {
        administratorService.deleteAdminFromBookClub(bookClubId, id);
    }

    //@RequestParam
    //@PathVariable
    //RequestBody needs a JSON in body

}