package ru.otus.catalog.services;

import java.util.List;

import ru.otus.catalog.dto.BookDto;

public interface BookService {

    BookDto findById(long id);

    List<BookDto> findAll();

    BookDto insert(String title, long authorId, List<Long> genresIds);

    BookDto update(long id, String title, long authorId, List<Long> genresIds);

    void deleteById(long id);
}
