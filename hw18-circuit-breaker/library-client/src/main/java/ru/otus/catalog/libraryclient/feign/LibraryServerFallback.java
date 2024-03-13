package ru.otus.catalog.libraryclient.feign;

import org.springframework.stereotype.Component;
import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.dto.CreateBookDto;
import ru.otus.catalog.dto.UpdateBookDto;
import ru.otus.catalog.libraryclient.cache.AbstractCache;

import java.util.List;

@Component
public class LibraryServerFallback implements LibraryServer {

    private final AbstractCache abstractCache;

    public LibraryServerFallback(AbstractCache abstractCache) {
        this.abstractCache = abstractCache;
    }

    @Override
    public List<BookDto> getAllBooks() {
        return abstractCache.getBooks();
    }

    @Override
    public BookDto getBookById(long id) {
        return abstractCache.getBookById(id);
    }

    @Override
    public BookDto insertBook(CreateBookDto bookCreateDto) {
        return abstractCache.insertBook(bookCreateDto);
    }

    @Override
    public BookDto updateBook(UpdateBookDto bookUpdateDto) {
        return abstractCache.updateBook(bookUpdateDto);
    }

    @Override
    public void deleteBook(long id) {

    }
}
