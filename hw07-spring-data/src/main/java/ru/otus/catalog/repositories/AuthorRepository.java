package ru.otus.catalog.repositories;

import java.util.List;
import java.util.Optional;

import ru.otus.catalog.models.Author;

public interface AuthorRepository {

    List<Author> findAll();

    Optional<Author> findById(long id);
}
