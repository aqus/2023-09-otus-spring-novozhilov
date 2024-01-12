package ru.otus.catalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.exceptions.EntityNotFoundException;
import ru.otus.catalog.mappers.BookMapper;
import ru.otus.catalog.models.Book;
import ru.otus.catalog.models.Comment;
import ru.otus.catalog.repositories.AuthorRepository;
import ru.otus.catalog.repositories.BookRepository;
import ru.otus.catalog.repositories.CommentRepository;
import ru.otus.catalog.repositories.GenreRepository;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class BookServiceImpl implements BookService {
    
    private final AuthorRepository authorRepository;
    
    private final GenreRepository genreRepository;
    
    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    public BookServiceImpl(AuthorRepository authorRepository,
                           GenreRepository genreRepository,
                           BookRepository bookRepository,
                           CommentRepository commentRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto findById(String id) {
        return BookMapper.toBookDto(
                bookRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(id)))
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
    public BookDto insert(String title, String authorId, List<String> genresIds) {
        return save(null, title, authorId, genresIds);
    }

    @Transactional
    @Override
    public BookDto update(String id, String title, String authorId, List<String> genresIds) {
        bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book with id %s is not found".formatted(id)));
        return save(id, title, authorId, genresIds);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
        List<Comment> commentsBookId = commentRepository.findAllByBookId(id);
        commentRepository.deleteAll(commentsBookId);
    }

    private BookDto save(String id, String title, String authorId, List<String> genresIds) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %s not found".formatted(authorId)));
        var genres = genreRepository.findAllById(genresIds);
        if (isEmpty(genres)) {
            throw new EntityNotFoundException("Genres with ids %s not found".formatted(genresIds));
        }
        Book book;

        if (id == null) {
            book = new Book(title, author, genres);
        } else {
            book = new Book(id, title, author, genres);
        }

        return BookMapper.toBookDto(bookRepository.save(book));
    }
}
