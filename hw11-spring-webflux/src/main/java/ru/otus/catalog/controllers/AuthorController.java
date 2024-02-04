package ru.otus.catalog.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.catalog.dto.AuthorDto;
import ru.otus.catalog.mappers.AuthorMapper;
import ru.otus.catalog.repositories.AuthorRepository;

@RestController
public class AuthorController {
    
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("api/v1/authors")
    public Flux<AuthorDto> findAllAuthors() {
        return authorRepository.findAll().map(AuthorMapper::toAuthorDto);
    }
}
