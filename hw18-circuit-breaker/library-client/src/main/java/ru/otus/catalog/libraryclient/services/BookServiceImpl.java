package ru.otus.catalog.libraryclient.services;

import org.springframework.stereotype.Service;
import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.dto.CreateBookDto;
import ru.otus.catalog.dto.UpdateBookDto;
import ru.otus.catalog.libraryclient.feign.LibraryServer;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final LibraryServer libraryServer;

    public BookServiceImpl(LibraryServer libraryServer) {
        this.libraryServer = libraryServer;
    }

    @Override
    public BookDto findBookById(long id) {
        return libraryServer.getBookById(id);
    }

    @Override
    public List<BookDto> findAll() {
        return libraryServer.getAllBooks();
    }

    @Override
    public BookDto create(CreateBookDto bookCreateDto) {
        return libraryServer.insertBook(bookCreateDto);
    }

    @Override
    public BookDto update(UpdateBookDto bookUpdateDto) {
        return libraryServer.updateBook(bookUpdateDto);
    }

    @Override
    public void deleteById(long id) {
        libraryServer.deleteBook(id);
    }
}
