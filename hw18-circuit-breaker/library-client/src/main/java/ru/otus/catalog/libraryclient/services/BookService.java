package ru.otus.catalog.libraryclient.services;

import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.dto.CreateBookDto;
import ru.otus.catalog.dto.UpdateBookDto;

import java.util.List;

public interface BookService {
    BookDto findBookById(long id);

    List<BookDto> findAll();

    BookDto create(CreateBookDto bookCreateDto);

    BookDto update(UpdateBookDto bookUpdateDto);

    void deleteById(long id);
}
