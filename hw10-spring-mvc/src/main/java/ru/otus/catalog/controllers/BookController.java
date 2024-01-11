package ru.otus.catalog.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.otus.catalog.converters.BookConverter;
import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.services.BookService;

@ShellComponent
public class BookController {

    private final BookService bookService;

    private final BookConverter bookConverter;

    public BookController(BookService bookService, BookConverter bookConverter) {
        this.bookService = bookService;
        this.bookConverter = bookConverter;
    }

    @ShellMethod(value = "Find all books", key = "ab")
    public String findAllBooks() {
        return bookService.findAll().stream()
                .map(bookConverter::bookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find book by id", key = "bbid")
    public String findBookById(long id) {
        return bookConverter.bookToString(bookService.findById(id));
    }

    //bins aaaaaaaaaaaaa 1 1,6
    //bins aaaaaaaaaaaaa 1 1,6
    @ShellMethod(value = "Insert book", key = "bins")
    public String insertBook(String title, long authorId, List<Long> genresIds) {
        BookDto savedBook = bookService.insert(title, authorId, genresIds);
        return bookConverter.bookToString(savedBook);
    }

    //bupd 4 dfasdfasdfasd 3 2,5
    @ShellMethod(value = "Update book", key = "bupd")
    public String updateBook(long id, String title, long authorId, List<Long> genresIds) {
        BookDto savedBook = bookService.update(id, title, authorId, genresIds);
        return bookConverter.bookToString(savedBook);
    }

    @ShellMethod(value = "Delete book by id", key = "bdel")
    public void updateBook(long id) {
        bookService.deleteById(id);
    }
}
