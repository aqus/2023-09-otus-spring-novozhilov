package ru.otus.catalog.libraryclient.cache;

import org.springframework.stereotype.Component;
import ru.otus.catalog.dto.AuthorDto;
import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.dto.CreateBookDto;
import ru.otus.catalog.dto.GenreDto;
import ru.otus.catalog.dto.UpdateBookDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AbstractCache {

    public List<BookDto> getBooks() {
        return List.of(new BookDto(1L, "Such an interesting book", null, null));
    }

    public BookDto getBookById(long id) {
        return new BookDto(id, "Such an interesting book", null, null);
    }

    public BookDto insertBook(CreateBookDto bookCreateDto) {
        return new BookDto(1L, bookCreateDto.getTitle(), new AuthorDto(bookCreateDto.getAuthorId(), ""),
                bookCreateDto.getGenresIds().stream()
                        .map(id -> new GenreDto(id, "")).collect(Collectors.toList()));
    }

    public BookDto updateBook(UpdateBookDto bookUpdateDto) {
        return new BookDto(1L, bookUpdateDto.getTitle(), new AuthorDto(bookUpdateDto.getAuthorId(), ""),
                bookUpdateDto.getGenresIds().stream()
                        .map(id -> new GenreDto(id, "")).collect(Collectors.toList()));
    }

}
