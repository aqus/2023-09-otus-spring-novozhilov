package ru.otus.catalog.services;

import java.util.List;

import ru.otus.catalog.dto.BookDto;

public interface BookService {

    BookDto findById(String id);

    List<BookDto> findAll();

    BookDto insert(String title, String authorId, List<String> genresIds);

    BookDto update(String id, String title, String authorId, List<String> genresIds);

    void deleteById(String id);
}
