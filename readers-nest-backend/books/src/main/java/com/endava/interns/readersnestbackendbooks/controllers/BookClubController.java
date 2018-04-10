package com.endava.interns.readersnestbackendbooks.controllers;

import com.endava.interns.readersnestbackendbooks.persistence.entities.BookClub;
import com.endava.interns.readersnestbackendbooks.persistence.repositories.BookClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/bookclub")
public class BookClubController {

    @Autowired
    private BookClubRepository bookClubRepository;


    /*private BookClubService bookClubService;

    @Autowired
    public BookClubController(BookClubService bookClubService) { this.bookClubService = bookClubService; }*/


    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<BookClub> getAllBookClubs() {
        return bookClubRepository.findAll();
    }

    @GetMapping(path = "/test")
    @ResponseBody
    public String test() {
        return "Hola mundo";
    }

    /*
    @GetMapping(path = "/{bookClubId}")
    @ResponseBody
    public Optional<BookClub> getBookClubById(@PathVariable("bookClubId") Long id) {
        return bookClubRepository.findById(id);
    }

    @PostMapping(path = "/saveBookClub")
    public BookClub saveBookClub(@Valid @RequestBody BookClub bookClub) {
        return bookClubRepository.save(bookClub);
    }

    @PutMapping(path = "/updateBookClub/{id}")
    public BookClub updateBookClub(@PathVariable Long id, @Valid @RequestBody BookClub bookClub) {
        BookClub bk = bookClubRepository.findById(id).orElseThrow(() -> new NullPointerException("Book not found"));

        bk.setName(bookClub.getName());
        bk.setDescription(bookClub.getDescription());
        bk.setActualBookId(bookClub.getActualBookId());
        bk.setIdAdmins(bookClub.getIdAdmins());
        bk.setIdMembers(bookClub.getIdMembers());
        bk.setMessages(bookClub.getMessages());

        return bookClubRepository.save(bk);
    }

    @DeleteMapping("/deleteBookClub/{id}")
    public void deleteBookClub(@PathVariable(value = "id") Long bookClubId) {
        BookClub bk = bookClubRepository.findById(bookClubId)
                .orElseThrow(() -> new NullPointerException("Book not found"));

        bookClubRepository.delete(bk);
    }*/


    //@RequestParam
    //@PathVariable
    //RequestBody needs a JSON in body

}
