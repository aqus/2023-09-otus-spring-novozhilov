package ru.otus.catalog.repositories;

import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.catalog.models.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Jpa репозиторий книг")
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName(" должен возвращать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        Book expectedBook = testEntityManager.find(Book.class, 1);;
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

    private List<Book> getBooks() {
        TypedQuery<Book> query = testEntityManager.getEntityManager()
                .createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }
}
