package ru.otus.catalog.repositories;

import java.util.List;

import ru.otus.catalog.models.Genre;

public interface GenreRepository {

    List<Genre> findAll();

    List<Genre> findAllByIds(List<Long> ids);
}
