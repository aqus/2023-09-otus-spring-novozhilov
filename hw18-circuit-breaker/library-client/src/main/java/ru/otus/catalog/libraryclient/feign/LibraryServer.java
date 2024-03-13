package ru.otus.catalog.libraryclient.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.dto.CreateBookDto;
import ru.otus.catalog.dto.UpdateBookDto;

import java.util.List;

@FeignClient(name = "library-server")
public interface LibraryServer {

    @CircuitBreaker(name = "getBooks", fallbackMethod = "buildFallbackBooks")
    @GetMapping("/api/v1/books")
    List<BookDto> getAllBooks();

    default List<BookDto> buildFallbackBooks(Throwable throwable) {
        return List.of(new BookDto(1L, "fallbackBook", null, null));
    }

    @CircuitBreaker(name = "getBook", fallbackMethod = "buildFallbackBook")
    @GetMapping("/api/v1/books/{id}")
    BookDto getBookById(@PathVariable("id") long id);

    default BookDto buildFallbackBook(Throwable throwable) {
        return new BookDto(1L, "fallbackBook", null, null);
    }

    @CircuitBreaker(name = "saveBook", fallbackMethod = "buildFallbackBook")
    @PostMapping("/api/v1/books")
    BookDto insertBook(@RequestBody @Valid CreateBookDto bookCreateDto);

    @CircuitBreaker(name = "updateBook", fallbackMethod = "buildFallbackBook")
    @PutMapping("/api/v1/books")
    BookDto updateBook(@RequestBody @Valid UpdateBookDto bookUpdateDto);

    @DeleteMapping("/api/v1/books/{id}")
    void deleteBook(@PathVariable("id") long id);
}
