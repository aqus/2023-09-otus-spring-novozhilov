package ru.otus.catalog.repositories;

import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.catalog.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Jpa репозиторий авторов")
@DataJpaTest
@Import({GenreRepositoryJpa.class})
public class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private List<Genre> dbGenres;

    @BeforeEach
    void setUp() {
        dbGenres = getDbGenres();
    }

    @DisplayName(" должен возвращать список всех жанров")
    @Test
    void shouldReturnAllTheGenres() {
        List<Genre> allGenres = genreRepository.findAll();
        List<Genre> expectedGenres = dbGenres;
        assertThat(allGenres).containsExactlyElementsOf(expectedGenres);
    }

    @DisplayName(" должен возвращать список жанров по id")
    @Test
    void shouldReturnGenresByIds() {
        Genre expectedGenre1 = dbGenres.get(0);
        Genre expectedGenre2 = dbGenres.get(1);
        List<Genre> actualGenres = genreRepository.findAllByIds(
                List.of(expectedGenre1.getId(), expectedGenre2.getId()));
        assertThat(actualGenres).containsExactlyElementsOf(List.of(expectedGenre1, expectedGenre2));
    }

    @DisplayName(" должен возвращать тот же жанр по id")
    @Test
    void shouldReturnSameGenreById() {
        Genre expectedGenre = dbGenres.get(0);
        List<Genre> actualGenres = genreRepository.findAllByIds(
                List.of(expectedGenre.getId()));
        Optional<Genre> actualGenre = actualGenres.stream().findFirst();
        assertThat(actualGenre)
                .isPresent()
                .get()
                .isEqualTo(expectedGenre);
    }

    @DisplayName(" должен возвращать пустой результат для неверной id")
    @Test
    void shouldReturnEmptyResultByWrongIds() {
        List<Genre> actualGenres = genreRepository.findAllByIds(
                List.of(10L));
        assertThat(actualGenres).isEmpty();
    }

    private List<Genre> getDbGenres() {
        TypedQuery<Genre> query = testEntityManager.getEntityManager()
                .createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }
}
