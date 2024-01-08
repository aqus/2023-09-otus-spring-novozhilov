package ru.otus.catalog.mappers;

import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.models.Book;

import java.util.stream.Collectors;

public class BookMapper {

    public static BookDto toBookDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), AuthorMapper.toAuthorDto(book.getAuthor()),
                book.getGenres().stream().map(GenreMapper::toGenreDto).collect(Collectors.toList()));
    }
}
