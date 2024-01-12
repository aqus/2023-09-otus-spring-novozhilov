package ru.otus.catalog.controllers;

import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.catalog.converters.BookConverter;
import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.dto.CreateBookDto;
import ru.otus.catalog.dto.UpdateBookDto;
import ru.otus.catalog.services.BookService;

@RestController("/api/v1")
public class BookController {

    private final BookService bookService;

    private final BookConverter bookConverter;

    public BookController(BookService bookService, BookConverter bookConverter) {
        this.bookService = bookService;
        this.bookConverter = bookConverter;
    }

    @GetMapping("/books")
    public String findAllBooks() {
        return bookService.findAll().stream()
                .map(bookConverter::bookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @GetMapping("/books/{id}")
    public String findBookById(@PathVariable long id) {
        return bookConverter.bookToString(bookService.findById(id));
    }

    @PostMapping("/books")
    public String insertBook(@RequestBody @Valid CreateBookDto createBookDto) {
        BookDto savedBook = bookService.insert(createBookDto.getTitle(), createBookDto.getAuthorId(),
                createBookDto.getGenresIds());
        return bookConverter.bookToString(savedBook);
    }

    @PutMapping("/books")
    public String updateBook(@RequestBody @Valid UpdateBookDto updateBookDto) {
        BookDto savedBook = bookService.update(updateBookDto.getId(), updateBookDto.getTitle(),
                updateBookDto.getAuthorId(), updateBookDto.getGenresIds());
        return bookConverter.bookToString(savedBook);
    }

    @DeleteMapping("/books/{id}")
    public void updateBook(@PathVariable long id) {
        bookService.deleteById(id);
    }
}
