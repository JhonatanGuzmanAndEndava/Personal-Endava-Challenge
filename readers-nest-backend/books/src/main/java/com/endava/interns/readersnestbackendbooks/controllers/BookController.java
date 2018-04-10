package com.endava.interns.readersnestbackendbooks.controllers;

import com.endava.interns.readersnestbackendbooks.exceptions.BookNotFoundException;
import com.endava.interns.readersnestbackendbooks.exceptions.CustomException;
import com.endava.interns.readersnestbackendbooks.persistence.entities.Book;
import com.endava.interns.readersnestbackendbooks.response.ResponseMessage;
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
    public ResponseMessage<Book> addNewBook(@RequestBody Book newBook){
        Book book = bookService.createBook(newBook);
        return new ResponseMessage<>(book);
    }

    @GetMapping(path = "/{bookId}")
    public ResponseMessage<Book> findBookById(@PathVariable("bookId") String id) throws CustomException {

        Optional<Book> optionalBook = bookService.readBook(id);
        if(!optionalBook.isPresent()) throw new BookNotFoundException();

        return new ResponseMessage<>(optionalBook.get());
    }

    @GetMapping
    public ResponseMessage<List<Book>> getAllBooks(){

        return new ResponseMessage<>(bookService.findAll());
    }

    @PutMapping(path = "/{bookId}")
    public ResponseMessage<Book> updateBook(@RequestBody Book updatedBook, @PathVariable("bookId") String bookId) throws BookNotFoundException{

        Optional<Book> optionalBook = bookService.updateBook(bookId, updatedBook);
        if(!optionalBook.isPresent()) throw new BookNotFoundException();
        return new ResponseMessage<>(optionalBook.get());
    }

    @DeleteMapping(path = "/{bookId}")
    public ResponseMessage<String> deleteBook(@PathVariable("bookId") String id) throws BookNotFoundException{

        Optional<String> optionalBook = bookService.deleteBook(id);
        if(!optionalBook.isPresent()) throw new BookNotFoundException();
        return new ResponseMessage<>(optionalBook.get());
    }
}
