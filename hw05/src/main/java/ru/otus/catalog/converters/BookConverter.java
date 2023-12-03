package ru.otus.catalog.converters;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ru.otus.catalog.models.Book;

@Component
public class BookConverter {
    
    private final AuthorConverter authorConverter;
    
    private final GenreConverter genreConverter;

    public BookConverter(AuthorConverter authorConverter, GenreConverter genreConverter) {
        this.authorConverter = authorConverter;
        this.genreConverter = genreConverter;
    }

    public String bookToString(Book book) {
        var genresString = book.getGenres().stream()
                .map(genreConverter::genreToString)
                .map("{%s}"::formatted)
                .collect(Collectors.joining(", "));
        return "Id: %d, title: %s, author: {%s}, genres: [%s]".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.authorToString(book.getAuthor()),
                genresString);
    }
}
