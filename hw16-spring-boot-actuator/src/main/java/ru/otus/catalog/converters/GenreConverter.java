package ru.otus.catalog.converters;

import org.springframework.stereotype.Component;

import ru.otus.catalog.dto.GenreDto;

@Component
public class GenreConverter {

    public String genreToString(GenreDto genre) {
        return "Id: %d, Name: %s".formatted(genre.getId(), genre.getName());
    }
}
