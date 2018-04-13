package com.endava.interns.readersnestbackendbookclubs.controllers;

import com.endava.interns.readersnestbackendbookclubs.exceptions.DuplicatedException;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Administrator;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.BookClub;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Member;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Message;
import com.endava.interns.readersnestbackendbookclubs.security.AuthService;
import com.endava.interns.readersnestbackendbookclubs.security.JwtTokenProvider;
import com.endava.interns.readersnestbackendbookclubs.services.AdministratorService;
import com.endava.interns.readersnestbackendbookclubs.services.BookClubService;
import com.endava.interns.readersnestbackendbookclubs.services.MemberService;
import com.endava.interns.readersnestbackendbookclubs.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.endava.interns.readersnestbackendbookclubs.security.SecurityConstants.HEADER_STRING;

@RestController
@RequestMapping(path = "/bookclub")
public class BookClubController {

    private BookClubService bookClubService;
    private MessageService messageService;
    private AdministratorService administratorService;
    private MemberService memberService;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public BookClubController(BookClubService bookClubService,
            MessageService messageService,
            AdministratorService administratorService,
            MemberService memberService, AuthService authService,
            JwtTokenProvider jwtTokenProvider)
    {
        this.bookClubService = bookClubService;
        this.messageService = messageService;
        this.administratorService = administratorService;
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
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
    public BookClub saveBookClub(@RequestHeader(value = HEADER_STRING) String authHeader, @RequestBody BookClub bookClub) {
        String adminId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(authHeader));

        Administrator admin = new Administrator();
        admin.setAdminId(adminId);
        admin.setBookClub(bookClub);
        bookClub.getAdmins().add(admin);

        Member member = new Member();
        member.setMemberId(adminId);
        member.setBookClub(bookClub);
        bookClub.getMembers().add(member);

        return bookClubService.createBookClub(bookClub);
    }

    @PutMapping(path = "/{id}")
    public BookClub updateBookClub(@RequestHeader(value = HEADER_STRING) String authHeader, @PathVariable Long id,
                                   @RequestBody BookClub bookClub) {
        String adminId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(authHeader));
        return bookClubService.updateBookClub(id, bookClub, adminId);
    }

    @DeleteMapping("/{id}")
    public void deleteBookClub(@RequestHeader(value = HEADER_STRING) String authHeader, @PathVariable(value = "id") Long bookClubId) {
        String adminId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(authHeader));
        bookClubService.deleteBookClub(bookClubId, adminId);
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
    public Message saveMessage( @RequestHeader(value = HEADER_STRING) String authHeader,
                                @PathVariable(value = "bookClubId") Long bookClubId, @RequestBody Message message) {
        String userId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(authHeader));
        return messageService.createMessage(bookClubId, message, userId);
    }

    //CRUD MEMBERS

    @GetMapping(path = "/{bookClubId}/members")
    @ResponseBody
    public Iterable<Member> getMembersFromBookClub( @PathVariable(value = "bookClubId") Long bookClubId ) {
        return memberService.getMembersFromBookClub(bookClubId);
    }

    @PostMapping(path = "/{bookClubId}/members")
    public Member addMember( @RequestHeader(value = HEADER_STRING) String authHeader,
                             @PathVariable(value = "bookClubId") Long bookClubId, @RequestBody Member member) throws DuplicatedException {
        String adminId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(authHeader));
        return memberService.addMemberToBookClub(bookClubId, member, adminId);
    }

    @DeleteMapping(path = "/{bookClubId}/members/{id}")
    public void deleteMember( @RequestHeader(value = HEADER_STRING) String authHeader,
                              @PathVariable(value = "bookClubId") Long bookClubId, @PathVariable(value = "id") String id ) {
        String adminId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(authHeader));
        memberService.deleteMemberFromBookClub(bookClubId, id, adminId);
    }

    //CRUD ADMINS

    @GetMapping(path = "/{bookClubId}/admins")
    @ResponseBody
    public Iterable<Administrator> getAdminsFromBookClub( @PathVariable(value = "bookClubId") Long bookClubId ) {
        return administratorService.getAdminsFromBookClub(bookClubId);
    }

    @PostMapping(path = "/{bookClubId}/admins")
    public Administrator addAdmin( @RequestHeader(value = HEADER_STRING) String authHeader,
                                   @PathVariable(value = "bookClubId") Long bookClubId, @RequestBody Administrator administrator) {
        String adminId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(authHeader));
        return administratorService.addAdminToBookClub(bookClubId, administrator, adminId);
    }

    @DeleteMapping(path = "/{bookClubId}/admins/{id}")
    public void deleteAdmin( @RequestHeader(value = HEADER_STRING) String authHeader,
                             @PathVariable(value = "bookClubId") Long bookClubId, @PathVariable(value = "id") String id ) {
        String otherAdminId = jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(authHeader));
        administratorService.deleteAdminFromBookClub(bookClubId, id, otherAdminId);
    }

    //@RequestParam
    //@PathVariable
    //RequestBody needs a JSON in body

}