package ru.otus.catalog.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.catalog.dto.GenreDto;
import ru.otus.catalog.services.GenreService;

@RestController
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("api/v1/genres")
    public List<GenreDto> findAllGenres() {
        return genreService.findAll();
    }
}
