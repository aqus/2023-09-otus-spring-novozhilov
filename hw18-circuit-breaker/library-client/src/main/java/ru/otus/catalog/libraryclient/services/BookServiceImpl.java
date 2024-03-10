package ru.otus.catalog.libraryclient.services;

import org.springframework.stereotype.Service;
import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.dto.CreateBookDto;
import ru.otus.catalog.dto.UpdateBookDto;
import ru.otus.catalog.libraryclient.feign.LibraryServerProxy;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final LibraryServerProxy libraryServerProxy;

    public BookServiceImpl(LibraryServerProxy libraryServerProxy) {
        this.libraryServerProxy = libraryServerProxy;
    }

    @Override
    public BookDto findBookById(long id) {
        return libraryServerProxy.getBookById(id);
    }

    @Override
    public List<BookDto> findAll() {
        return libraryServerProxy.getAllBooks();
    }

    @Override
    public BookDto create(CreateBookDto bookCreateDto) {
        return libraryServerProxy.insertBook(bookCreateDto);
    }

    @Override
    public BookDto update(UpdateBookDto bookUpdateDto) {
        return libraryServerProxy.updateBook(bookUpdateDto);
    }

    @Override
    public void deleteById(long id) {
        libraryServerProxy.deleteBook(id);
    }
}
