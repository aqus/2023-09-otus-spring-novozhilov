package ru.otus.catalog.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.catalog.dto.GenreDto;
import ru.otus.catalog.repositories.GenreRepository;

import java.util.List;

@DisplayName("Контроллер жанров")
@SpringBootTest(classes = {GenreController.class})
class GenreControllerTest {

    @Autowired
    private GenreController genreController;

    @MockBean
    private GenreRepository genreRepository;

    private List<GenreDto> genres;
    
    @BeforeEach
    void setUp() {
        genres = List.of(
                new GenreDto("1", "Genre_1"),
                new GenreDto("2", "Genre_2"),
                new GenreDto("3", "Genre_3"),
                new GenreDto("4", "Genre_4"),
                new GenreDto("5", "Genre_5"),
                new GenreDto("6", "Genre_6")
        );
    }
    
    @DisplayName("должен возвращать список жанров")
    @Test
    void shouldReturnAllTheGenres() throws Exception {
        when(genreRepository.findAll()).thenReturn(Flux.fromStream(genres.stream().map(GenreDto::toModelObject)));

        WebTestClient testClient = WebTestClient.bindToController(genreController).build();
        testClient.get()
                .uri("/api/v1/genres")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(GenreDto.class)
                .isEqualTo(genres);

        verify(genreRepository, times(1)).findAll();
    }
}
