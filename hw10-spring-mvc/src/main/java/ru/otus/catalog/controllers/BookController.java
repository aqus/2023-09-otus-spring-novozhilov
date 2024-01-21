package ru.otus.catalog.controllers;

import java.util.List;

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
import ru.otus.catalog.services.BookService;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("api/v1/books")
    public List<BookDto> findAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("api/v1/books/{id}")
    public BookDto findBookById(@PathVariable long id) {
        return bookService.findById(id);
    }

    @PostMapping("api/v1/books")
    public BookDto insertBook(@RequestBody @Valid CreateBookDto createBookDto) {
        return bookService.insert(createBookDto.getTitle(), createBookDto.getAuthorId(),
                createBookDto.getGenresIds());
    }

    @PutMapping("api/v1/books")
    public BookDto updateBook(@RequestBody @Valid UpdateBookDto updateBookDto) {
        return bookService.update(updateBookDto.getId(), updateBookDto.getTitle(),
                updateBookDto.getAuthorId(), updateBookDto.getGenresIds());
    }

    @DeleteMapping("api/v1/books/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteById(id);
    }
}
