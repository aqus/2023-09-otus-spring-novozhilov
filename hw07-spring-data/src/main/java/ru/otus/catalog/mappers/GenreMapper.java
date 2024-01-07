package ru.otus.catalog.mappers;

import ru.otus.catalog.dto.GenreDto;
import ru.otus.catalog.models.Genre;

public class GenreMapper {

    public static GenreDto toGenreDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}
