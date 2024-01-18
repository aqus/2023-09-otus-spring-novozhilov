package ru.otus.catalog.converters;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ru.otus.catalog.dto.BookDto;

@Component
public class BookConverter {
    
    private final AuthorConverter authorConverter;
    
    private final GenreConverter genreConverter;

    public BookConverter(AuthorConverter authorConverter, GenreConverter genreConverter) {
        this.authorConverter = authorConverter;
        this.genreConverter = genreConverter;
    }

    public String bookToString(BookDto bookDto) {
        var genresString = bookDto.getGenreDtos().stream()
                .map(genreConverter::genreToString)
                .map("{%s}"::formatted)
                .collect(Collectors.joining(", "));
        return "Id: %s, title: %s, author: {%s}, genres: [%s]".formatted(
                bookDto.getId(),
                bookDto.getTitle(),
                authorConverter.authorToString(bookDto.getAuthorDto()),
                genresString);
    }
}
