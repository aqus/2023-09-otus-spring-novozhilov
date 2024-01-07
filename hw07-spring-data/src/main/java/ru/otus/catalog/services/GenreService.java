package ru.otus.catalog.services;

import java.util.List;

import ru.otus.catalog.dto.GenreDto;

public interface GenreService {

    List<GenreDto> findAll();
}
