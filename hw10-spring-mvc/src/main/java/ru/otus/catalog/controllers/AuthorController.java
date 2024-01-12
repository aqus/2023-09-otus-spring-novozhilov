package ru.otus.catalog.controllers;

import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.catalog.converters.AuthorConverter;
import ru.otus.catalog.services.AuthorService;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {
    
    private final AuthorService authorService;
    
    private final AuthorConverter authorConverter;

    public AuthorController(AuthorService authorService, AuthorConverter authorConverter) {
        this.authorService = authorService;
        this.authorConverter = authorConverter;
    }

    @GetMapping
    public String findAllAuthors() {
        return authorService.findAll().stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }
}
