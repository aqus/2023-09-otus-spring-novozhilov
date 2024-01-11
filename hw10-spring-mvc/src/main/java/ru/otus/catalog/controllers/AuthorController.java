package ru.otus.catalog.controllers;

import java.util.stream.Collectors;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.otus.catalog.converters.AuthorConverter;
import ru.otus.catalog.services.AuthorService;

@ShellComponent
public class AuthorController {
    
    private final AuthorService authorService;
    
    private final AuthorConverter authorConverter;

    public AuthorController(AuthorService authorService, AuthorConverter authorConverter) {
        this.authorService = authorService;
        this.authorConverter = authorConverter;
    }

    @ShellMethod(value = "Find all authors", key = "aa")
    public String findAllAuthors() {
        return authorService.findAll().stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }
}
