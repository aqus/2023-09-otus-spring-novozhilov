package ru.otus.catalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.exceptions.EntityNotFoundException;
import ru.otus.catalog.mappers.BookMapper;
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

    @Transactional(readOnly = true)
    @Override
    public BookDto findById(long id) {
        return BookMapper.toBookDto(
                bookRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toBookDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BookDto insert(String title, long authorId, List<Long> genresIds) {
        return save(0, title, authorId, genresIds);
    }

    @Transactional
    @Override
    public BookDto update(long id, String title, long authorId, List<Long> genresIds) {
        bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book with id %d is not found".formatted(id)));
        return save(id, title, authorId, genresIds);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private BookDto save(long id, String title, long authorId, List<Long> genresIds) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genres = genreRepository.findAllById(genresIds);
        if (isEmpty(genres)) {
            throw new EntityNotFoundException("Genres with ids %s not found".formatted(genresIds));
        }
        var book = new Book(id, title, author, genres);
        return BookMapper.toBookDto(bookRepository.save(book));
    }
}
