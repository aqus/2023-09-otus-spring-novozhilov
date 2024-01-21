package ru.otus.catalog.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.catalog.dto.AuthorDto;
import ru.otus.catalog.services.AuthorService;

@RestController
public class AuthorController {
    
    private final AuthorService authorService;
    
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("api/v1/authors")
    public List<AuthorDto> findAllAuthors() {
        return authorService.findAll();
    }
}
