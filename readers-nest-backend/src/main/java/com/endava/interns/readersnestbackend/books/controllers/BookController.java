package com.endava.interns.readersnestbackend.books.controllers;

import com.endava.interns.readersnestbackend.books.persistence.entities.Book;
import com.endava.interns.readersnestbackend.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public @ResponseBody
    Book addNewBook(@RequestBody Book newBook){
        return bookService.createBook(newBook);
    }

    @GetMapping(path = "/{bookId}")
    public @ResponseBody
    Book findBookById(@PathVariable("bookId") String id){
        Optional<Book> optionalBook = bookService.readBook(id);
        return optionalBook.get();
    }

    @GetMapping
    public @ResponseBody
    List<Book> getAllBooks(){
        return bookService.findAll();
    }

    @PutMapping(path = "/{bookId}")
    public @ResponseBody
    Book updateBook(@RequestBody Book updatedBook){
        return bookService.updateBook(updatedBook);
    }

    @DeleteMapping(path = "/{bookId}")
    public @ResponseBody
    String deleteBook(@PathVariable("bookId") String id){
        return bookService.deleteBook(id);
    }
}
