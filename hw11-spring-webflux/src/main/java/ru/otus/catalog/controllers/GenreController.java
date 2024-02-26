package ru.otus.catalog.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.catalog.dto.GenreDto;
import ru.otus.catalog.mappers.GenreMapper;
import ru.otus.catalog.repositories.GenreRepository;

@RestController
public class GenreController {

    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping("api/v1/genres")
    public Flux<GenreDto> findAllGenres() {
        return genreRepository.findAll().map(GenreMapper::toGenreDto);
    }
}
