package ru.otus.catalog.services;

import java.util.List;
import java.util.Optional;

import ru.otus.catalog.models.Book;

public interface BookService {

    Optional<Book> findById(long id);

    List<Book> findAll();

    Book insert(String title, long authorId, List<Long> genresIds);

    Book update(long id, String title, long authorId, List<Long> genresIds);

    void deleteById(long id);
}
