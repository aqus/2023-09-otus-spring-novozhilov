package ru.otus.catalog.mappers;

import ru.otus.catalog.dto.AuthorDto;
import ru.otus.catalog.models.Author;

public class AuthorMapper {

    public static AuthorDto toAuthorDto(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }
}
