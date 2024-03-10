package ru.otus.catalog.libraryclient.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.dto.CreateBookDto;
import ru.otus.catalog.dto.UpdateBookDto;
import ru.otus.catalog.libraryclient.services.BookService;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/v1/books")
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/api/v1/books/{id}")
    public BookDto getBookById(@PathVariable("id") long id) {
        return bookService.findBookById(id);
    }

    @PostMapping("/api/v1/books")
    public BookDto addBook(@RequestBody @Valid CreateBookDto bookCreateDto) {
        return bookService.create(bookCreateDto);
    }

    @PutMapping("/api/v1/books")
    public BookDto updateBook(@RequestBody @Valid UpdateBookDto bookUpdateDto) {
        return bookService.update(bookUpdateDto);
    }

    @DeleteMapping("/api/v1/books/{id}")
    public void deleteBook (@PathVariable("id") long id) {
        bookService.deleteById(id);
    }
}
