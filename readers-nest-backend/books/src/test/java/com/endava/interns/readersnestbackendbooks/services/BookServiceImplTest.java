package com.endava.interns.readersnestbackendbooks.services;

import com.endava.interns.readersnestbackendbooks.persistence.entities.Book;
import com.endava.interns.readersnestbackendbooks.persistence.repositories.BookRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookServiceImplTest {

    private BookRepository bookRepository;

    private BookService bookService;

    @Before
    public void setUp(){

        bookRepository = mock(BookRepository.class);

        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    public void testFindBookThatExists(){

        Book book = mock(Book.class);
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.readBook("1");
        if(!foundBook.isPresent()) fail("The book should have been found");
    }

    @Test
    public void testFindBookNotExists(){

        when(bookRepository.findById("1")).thenReturn(Optional.empty());

        Optional<Book> foundBook = bookService.readBook("1");
        if(foundBook.isPresent()) fail("The book shouldn't have been found");
    }

    @Test
    public void testCreateBook(){

        Book book = new Book()
                .setId("1");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book newBook = bookService.createBook(new Book());

        assertNotNull("The book should have been created", newBook);
        assertEquals("1", newBook.getId());
    }

    @Test
    public void testUpdateExistingBook(){

        Book existingBook = new Book()
                .setId("1")
                .setAuthor("author")
                .setName("book");

        Book updatedBook = new Book()
                .setId("1")
                .setAuthor("author")
                .setName("book2");

        when(bookRepository.findById("1")).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book changesBook = new Book().setName("book2");

        Optional<Book> actualUpdatedBook = bookService.updateBook("1",changesBook);

        assertTrue("The book should have been upodated", actualUpdatedBook.isPresent());
        assertEquals("book2", actualUpdatedBook.get().getName());
        assertEquals("author", actualUpdatedBook.get().getAuthor());

    }

    @Test
    public void testUpdateNonExistingBook(){

        Book existingBook = new Book()
                .setId("1")
                .setAuthor("author")
                .setName("book");

        Book updatedBook = new Book()
                .setId("1")
                .setAuthor("author")
                .setName("book2");

        when(bookRepository.findById("1")).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book changesBook = new Book().setName("book2");

        Optional<Book> actualUpdatedBook = bookService.updateBook("1",changesBook);

        assertFalse("The book should't have been found", actualUpdatedBook.isPresent());
    }


    @Test
    public void testDeleteNonExistingBook(){

        when(bookRepository.existsById("1")).thenReturn(false);

        Optional<String> book = bookService.deleteBook("1");

        assertFalse("There shouldn't be a book", book.isPresent());


    }

    @Test
    public void testDeleteExistingBook(){

        when(bookRepository.existsById("1")).thenReturn(true);

        Optional<String> book = bookService.deleteBook("1");

        assertTrue("There should be a book", book.isPresent());
        assertEquals("1", book.get());
    }

    @Test
    public void testFindAll(){

        List<Book> empty = new ArrayList<>();
        List<Book> nonEmpty = new ArrayList<>();
        nonEmpty.add(new Book());
        nonEmpty.add(new Book());

        List<Book> act;

        when(bookRepository.findAll()).thenReturn(empty);

        act = bookService.findAll();
        assertEquals(0, act.size());

        when(bookRepository.findAll()).thenReturn(nonEmpty);

        act = bookService.findAll();
        assertEquals(2, act.size());

    }
}