package ru.otus.catalog.repositories;

import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.catalog.models.Author;
import ru.otus.catalog.models.Book;
import ru.otus.catalog.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Jpa репозиторий книг")
@DataJpaTest
@Import({BookRepositoryJpa.class})
public class BookRepositoryJpaTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager testEntityManager;

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
        assertThat(actualBooks).isNotNull().isEqualTo(expectedBooks);
    }

    @DisplayName(" должен сохранять книгу")
    @Test
    void shouldSaveBook() {
        Book expectedBook = new Book(0, "BookTitle_4", getAuthors().get(1),
                List.of(getGenres().get(0), getGenres().get(1)));
        Book actualBook = bookRepository.save(expectedBook);
        assertThat(actualBook).isNotNull().isEqualTo(expectedBook);
    }

    @DisplayName(" должен обновлять книгу")
    @Test
    void shouldUpdateBook() {
        Book expectedBook = new Book(1, "NewBookTitle_1", getAuthors().get(2),
                List.of(getGenres().get(2), getGenres().get(3)));

        Book oldBook = testEntityManager.find(Book.class, expectedBook.getId());
        assertThat(oldBook)
                .isNotNull()
                .isNotEqualTo(expectedBook);

        Book changedBook = bookRepository.save(expectedBook);
        assertThat(changedBook)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);

        Book foundNewBook = testEntityManager.find(Book.class, expectedBook.getId());
        assertThat(foundNewBook)
                .isNotNull()
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

    private List<Book> getBooks() {
        TypedQuery<Book> query = testEntityManager.getEntityManager()
                .createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    private  List<Author> getAuthors() {
        TypedQuery<Author> query = testEntityManager.getEntityManager()
                .createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    private List<Genre> getGenres() {
        TypedQuery<Genre> query = testEntityManager.getEntityManager()
                .createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }
}
