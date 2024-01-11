package ru.otus.catalog.controllers;

import java.util.stream.Collectors;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.otus.catalog.converters.GenreConverter;
import ru.otus.catalog.services.GenreService;

@ShellComponent
public class GenreController {

    private final GenreService genreService;

    private final GenreConverter genreConverter;

    public GenreController(GenreService genreService, GenreConverter genreConverter) {
        this.genreService = genreService;
        this.genreConverter = genreConverter;
    }

    @ShellMethod(value = "Find all genres", key = "ag")
    public String findAllGenres() {
        return genreService.findAll().stream()
                .map(genreConverter::genreToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }
}
