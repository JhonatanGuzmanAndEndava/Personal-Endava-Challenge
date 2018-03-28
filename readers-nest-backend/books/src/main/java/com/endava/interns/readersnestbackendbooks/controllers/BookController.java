package com.endava.interns.readersnestbackendbooks.controllers;

import com.endava.interns.readersnestbackendbooks.persistence.entities.Book;
import com.endava.interns.readersnestbackendbooks.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Book addNewBook(@RequestBody Book newBook){
        return bookService.createBook(newBook);
    }

    @GetMapping(path = "/{bookId}")
    public ResponseEntity<Book> findBookById(@PathVariable("bookId") String id){
        Optional<Book> optionalBook = bookService.readBook(id);
        return optionalBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.findAll();
    }

    @PutMapping(path = "/{bookId}")
    public ResponseEntity<Book> updateBook(@RequestBody Book updatedBook){
        Optional<Book> optionalBook = bookService.updateBook(updatedBook);
        return optionalBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping(path = "/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable("bookId") String id){
        Optional<String> optionalBook = bookService.deleteBook(id);
        return optionalBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
