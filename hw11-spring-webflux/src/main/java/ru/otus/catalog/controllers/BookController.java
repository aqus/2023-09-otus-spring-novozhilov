package ru.otus.catalog.controllers;

import jakarta.validation.Valid;

import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.Validate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.dto.CreateBookDto;
import ru.otus.catalog.dto.UpdateBookDto;
import ru.otus.catalog.exceptions.EntityNotFoundException;
import ru.otus.catalog.mappers.BookMapper;
import ru.otus.catalog.models.Author;
import ru.otus.catalog.models.Book;
import ru.otus.catalog.models.Genre;
import ru.otus.catalog.repositories.AuthorRepository;
import ru.otus.catalog.repositories.BookRepository;
import ru.otus.catalog.repositories.GenreRepository;

import java.util.List;

@RestController
public class BookController {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    public BookController(BookRepository bookRepository,
                          AuthorRepository authorRepository,
                          GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping("api/v1/books")
    public Flux<BookDto> findAllBooks() {
        return bookRepository.findAll().map(BookMapper::toBookDto);
    }

    @GetMapping("api/v1/books/{id}")
    public Mono<BookDto> findBookById(@PathVariable long id) {
        return bookRepository.findById(id).map(BookMapper::toBookDto);
    }

    @PostMapping("api/v1/books")
    public Mono<BookDto> insertBook(@RequestBody @Valid CreateBookDto createBookDto) {
        return saveBook(0L, createBookDto.getTitle(), createBookDto.getAuthorId(), createBookDto.getGenresIds());
    }

    @PutMapping("api/v1/books")
    public Mono<BookDto> updateBook(@RequestBody @Valid UpdateBookDto updateBookDto) {
        return bookRepository.findById(updateBookDto.getId())
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Book with id %d is not found"
                        .formatted(updateBookDto.getId()))))
                .zipWith(saveBook(updateBookDto.getId(), updateBookDto.getTitle(),
                        updateBookDto.getAuthorId(), updateBookDto.getGenresIds()))
                .map(Tuple2::getT2);
    }

    @DeleteMapping("api/v1/books/{id}")
    public void deleteBook(@PathVariable long id) {
        bookRepository.deleteById(id);
    }

    private Mono<BookDto> saveBook(@NotNull Long id,
                                   String title,
                                   Long authorId,
                                   List<Long> genreIds) {
        Validate.notNull(id);

        Mono<Author> authorMono = authorRepository.findById(authorId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Book with id %d is not found"
                        .formatted(authorId))));
        Flux<Genre> genres = genreRepository.findAllById(genreIds)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Genres with ids %s not found"
                        .formatted(genreIds))));

        return genres.collectList()
                .zipWith(authorMono)
                .flatMap(relations -> {
                    Book book = new Book(id, title, relations.getT2(), relations.getT1());
                    return bookRepository.save(book).map(BookMapper::toBookDto);
                });
    }
}
