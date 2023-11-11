package ru.otus.catalog.services;

import java.util.List;

import ru.otus.catalog.models.Author;

public interface AuthorService {

    List<Author> findAll();
}
