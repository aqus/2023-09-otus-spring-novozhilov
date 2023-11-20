package ru.otus.catalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ru.otus.catalog.exceptions.EntityNotFoundException;
import ru.otus.catalog.models.Book;
import ru.otus.catalog.repositories.AuthorRepository;
import ru.otus.catalog.repositories.BookRepository;
import ru.otus.catalog.repositories.GenreRepository;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class BookServiceImpl implements BookService {
    
    private final AuthorRepository authorRepository;
    
    private final GenreRepository genreRepository;
    
    private final BookRepository bookRepository;

    public BookServiceImpl(AuthorRepository authorRepository,
                           GenreRepository genreRepository,
                           BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book insert(String title, long authorId, List<Long> genresIds) {
        return save(0, title, authorId, genresIds);
    }

    @Override
    public Book update(long id, String title, long authorId, List<Long> genresIds) {
        bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book with id %d is not found".formatted(id)));
        return save(id, title, authorId, genresIds);
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private Book save(long id, String title, long authorId, List<Long> genresIds) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genres = genreRepository.findAllByIds(genresIds);
        if (isEmpty(genres)) {
            throw new EntityNotFoundException("Genres with ids %s not found".formatted(genresIds));
        }
        var book = new Book(id, title, author, genres);
        return bookRepository.save(book);
    }
}
