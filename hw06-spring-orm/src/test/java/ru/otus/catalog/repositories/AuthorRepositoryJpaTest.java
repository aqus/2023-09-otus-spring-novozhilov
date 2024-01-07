package ru.otus.catalog.repositories;

import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.catalog.models.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Jpa репозиторий авторов")
@DataJpaTest
@Import({AuthorRepositoryJpa.class})
public class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private List<Author> dbAuthors;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
    }

    @DisplayName(" должен загржуать всех авторов")
    @Test
    void shouldLoadAllTheAuthors() {
        List<Author> allAuthors = authorRepository.findAll();
        List<Author> expectedAuthors = dbAuthors;
        assertThat(allAuthors).containsExactlyElementsOf(expectedAuthors);
    }

    @DisplayName(" должен загружать автора по id")
    @Test
    void shouldLoadAuthorById() {
        Author expectedAuthor = dbAuthors.get(0);
        Optional<Author> author = authorRepository.findById(expectedAuthor.getId());
        assertThat(author).isPresent().get().isEqualTo(expectedAuthor);
    }

    @DisplayName(" должен вернуть пустой результат для несуществующего id автора")
    @Test
    void shouldReturnEmptyResultForWrongAuthorId() {
        Optional<Author> author = authorRepository.findById(10L);
        assertThat(author).isNotPresent();
    }

    private List<Author> getDbAuthors() {
        TypedQuery<Author> query = testEntityManager.getEntityManager()
                .createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }
}
