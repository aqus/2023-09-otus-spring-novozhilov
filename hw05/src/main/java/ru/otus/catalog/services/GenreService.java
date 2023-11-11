package ru.otus.catalog.services;

import java.util.List;

import ru.otus.catalog.models.Genre;

public interface GenreService {

    List<Genre> findAll();
}
