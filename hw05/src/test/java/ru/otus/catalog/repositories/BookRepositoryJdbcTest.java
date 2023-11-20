package ru.otus.catalog.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.catalog.models.Author;
import ru.otus.catalog.models.Book;
import ru.otus.catalog.models.Genre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий книг BookRepositoryJdbc")
@Import({BookRepositoryJdbc.class})
@JdbcTest
public class BookRepositoryJdbcTest {

    @Autowired
    private BookRepository bookRepository;

    @MockBean
    private GenreRepository genreRepository;

    @DisplayName(" должен возвращать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        Book expectedBook = getBooks().get(0);
        Optional<Book> actual = bookRepository.findById(expectedBook.getId());
        assertThat(actual).isPresent().get().isEqualTo(expectedBook);
    }

    @DisplayName(" должен возвращать все книги")
    @Test
    void shouldReturnAllBooks() {
        List<Book> actualBooks = bookRepository.findAll();
        List<Book> expectedBooks = getBooks();

        assertThat(actualBooks)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("genres")
                .isEqualTo(expectedBooks);
    }

    @DisplayName(" должен сохранять книгу")
    @Test
    void shouldSaveBook() {
        Book expectedBook = new Book(0, "BookTitle_4", getAuthors().get(1),
                List.of(getGenres().get(0), getGenres().get(1)));
        Book actualBook = bookRepository.save(expectedBook);
        assertThat(actualBook)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedBook);
    }

    @DisplayName(" должен обновлять книгу")
    @Test
    void shouldUpdateBook() {
        Book expectedBook = new Book(1, "NewBookTitle_1", getAuthors().get(2),
                List.of(getGenres().get(2), getGenres().get(3)));

        Optional<Book> oldBook = bookRepository.findById(expectedBook.getId());
        assertThat(oldBook)
                .isPresent()
                .get()
                .isNotEqualTo(expectedBook);

        Book changedBook = bookRepository.save(expectedBook);
        assertThat(changedBook)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedBook);

        Optional<Book> foundNewBook = bookRepository.findById(expectedBook.getId());
        assertThat(foundNewBook)
                .isPresent()
                .get()
                .isEqualTo(changedBook);
    }

    @DisplayName(" должен удалять книгу по id")
    @Test
    void shouldDeleteBookById() {
        Book deletionBook = getBooks().get(0);
        bookRepository.deleteById(deletionBook.getId());
        Optional<Book> emptyResult = bookRepository.findById(deletionBook.getId());
        assertThat(emptyResult).isEmpty();
    }

    private static List<Book> getBooks() {
        Map<Long, List<Genre>> genresByBookId = new HashMap<>();
        genresByBookId.put(1L, List.of(getGenres().get(0), getGenres().get(1)));
        genresByBookId.put(2L, List.of(getGenres().get(2), getGenres().get(3)));
        genresByBookId.put(3L, List.of(getGenres().get(4), getGenres().get(5)));

        return IntStream.range(1,4)
                .boxed()
                .map(id -> new Book(id.longValue(), "BookTitle_" + id, getAuthors().get(id - 1),
                                genresByBookId.get(id.longValue())))
                .toList();
    }

    private static List<Author> getAuthors() {
        return IntStream.range(1,4)
                .boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private static List<Genre> getGenres() {
        return IntStream.range(1,7)
                .boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }
}
