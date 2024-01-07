package ru.otus.catalog.converters;

import org.springframework.stereotype.Component;

import ru.otus.catalog.dto.AuthorDto;

@Component
public class AuthorConverter {

    public String authorToString(AuthorDto authorDto) {
        return "Id: %d, FullName: %s".formatted(authorDto.getId(), authorDto.getFullName());
    }
}
