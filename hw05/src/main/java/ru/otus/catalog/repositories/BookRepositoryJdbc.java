package ru.otus.catalog.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ru.otus.catalog.models.Author;
import ru.otus.catalog.models.Book;
import ru.otus.catalog.models.Genre;

@Repository
public class BookRepositoryJdbc implements BookRepository {

    private static final Logger LOGGER = Logger.getLogger(BookRepositoryJdbc.class.getSimpleName());
    
    private final GenreRepository genreRepository;

    private final NamedParameterJdbcOperations namedParameterJdbc;

    public BookRepositoryJdbc(GenreRepository genreRepository, NamedParameterJdbcOperations namedParameterJdbc) {
        this.genreRepository = genreRepository;
        this.namedParameterJdbc = namedParameterJdbc;
    }

    @Override
    public Optional<Book> findById(long id) {
        try {
            List<Book> books = namedParameterJdbc.query(
                """
                        SELECT b.id, b.title, b.author_id, a.full_name, bg.genre_id, g.name
                        FROM books b
                        JOIN authors a ON a.id = b.author_id
                        LEFT JOIN books_genres bg ON bg.book_id = b.id
                        LEFT JOIN genres g ON g.id = bg.genre_id
                        WHERE b.id = :id
                    """,
                    Map.of("id", id),
                    new BookResultSetExtractor());
            if (books == null || books.isEmpty()) {
                return Optional.empty();
            }
            return Optional.ofNullable(books.get(0));
        } catch (DataAccessException e) {
            LOGGER.severe("While getting book by id " + e);
            return Optional.empty();
        }

    }

    @Override
    public List<Book> findAll() {
        List<Genre> genres = genreRepository.findAll();
        List<BookGenreRelation> relations = getAllGenreRelations();
        List<Book> books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbc.update("DELETE FROM books WHERE id = :id", Map.of("id", id));
    }

    private List<Book> getAllBooksWithoutGenres() {
        return namedParameterJdbc.query(
            """
                    SELECT b.id, b.title, b.author_id, a.full_name
                    FROM books b
                    LEFT JOIN authors a ON a.id = b.author_id
                """,
                new BookRowMapper());
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return namedParameterJdbc.query(
                "SELECT book_id, genre_id FROM books_genres",
                new BookGenreRowMapper());
    }

    /**
     * Добавляет книгам жанры в соответствии со связями
     * @param booksWithoutGenres - книги без жанров
     * @param genres             - список всех жанров
     * @param relations          - список всех связей книга-жанр
     */
    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {
        Map<Long, Genre> genreById = genres.stream().collect(Collectors.toMap(Genre::getId, Function.identity()));
        Map<Long, List<Long>> genresByBookId = relations
                .stream()
                .collect(
                        Collectors.groupingBy(
                                BookGenreRelation::bookId,
                                Collectors.mapping(BookGenreRelation::genreId, Collectors.toList())
                        )
                );

        for (Book book : booksWithoutGenres) {
            List<Genre> currentGenres = new ArrayList<>();
            List<Long> currentGenresIds = genresByBookId.get(book.getId());
            if (currentGenresIds == null) {
                continue;
            }
            currentGenresIds.forEach(gid -> currentGenres.add(genreById.get(gid)));
            book.setGenres(currentGenres);
        }
    }

    private Book insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("title", book.getTitle());
        mapSqlParameterSource.addValue("authorId", book.getAuthor().getId());

        namedParameterJdbc.update(
                "INSERT INTO books (title, author_id) VALUES (:title, :authorId)",
                mapSqlParameterSource,
                keyHolder);

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {
        namedParameterJdbc.update(
                "UPDATE books SET title=:title, author_id=:authorId WHERE id = :id",
                Map.of("id", book.getId(), "title", book.getTitle(), "authorId", book.getAuthor().getId()));

        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        if (book.getGenres() == null) {
            return;
        }

        List<BookGenreRelation> bgRelations = new ArrayList<>();
        book.getGenres().forEach(genre -> bgRelations.add(new BookGenreRelation(book.getId(), genre.getId())));
        namedParameterJdbc.batchUpdate(
                "INSERT INTO books_genres (book_id, genre_id) VALUES (:bookId, :genreId)",
                SqlParameterSourceUtils.createBatch(bgRelations));
    }

    private void removeGenresRelationsFor(Book book) {
        namedParameterJdbc.update("DELETE FROM books_genres WHERE book_id = :id", Map.of("id", book.getId()));
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author(rs.getLong("author_id"), rs.getString("full_name"));
            return new Book(rs.getLong("id"), rs.getString("title"), author, null);
        }
    }

    private static class BookResultSetExtractor implements ResultSetExtractor<List<Book>> {

        @Override
        public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Book> books = new HashMap<>();

            while (rs.next()) {
                long id = rs.getLong("id");
                String title = rs.getString("title");
                Author author = new Author(rs.getLong("author_id"), rs.getString("full_name"));
                List<Genre> genres;
                Book prevBook = books.get(id);
                if (prevBook != null) {
                    genres = prevBook.getGenres();
                } else {
                    genres = new ArrayList<>();
                }
                Long genreId = rs.getObject("genre_id", Long.class);
                if (genreId != null) {
                    String genreName = rs.getString("name");
                    genres.add(new Genre(genreId, genreName));
                }
                Book bookToAdd = new Book(id, title, author, genres);
                books.put(id, bookToAdd);
            }
            return new ArrayList<>(books.values());
        }
    }

    private static class BookGenreRowMapper implements RowMapper<BookGenreRelation> {

        @Override
        public BookGenreRelation mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new BookGenreRelation(rs.getLong("book_id"), rs.getLong("genre_id"));
        }
    }

    private record BookGenreRelation(long bookId, long genreId) {
    }
}
