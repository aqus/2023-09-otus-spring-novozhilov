package ru.otus.catalog.converters;

import org.springframework.stereotype.Component;

import ru.otus.catalog.models.Author;

@Component
public class AuthorConverter {

    public String authorToString(Author author) {
        return "Id: %d, FullName: %s".formatted(author.getId(), author.getFullName());
    }
}
