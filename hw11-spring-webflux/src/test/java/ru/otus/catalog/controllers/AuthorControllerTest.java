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
import ru.otus.catalog.dto.AuthorDto;
import ru.otus.catalog.repositories.AuthorRepository;

import java.util.List;

@DisplayName("Контроллер авторов")
@SpringBootTest(classes = {AuthorController.class})
class AuthorControllerTest {

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorController authorController;
    
    private List<AuthorDto> authorsDto;

    @BeforeEach
    void setUp() {
        authorsDto = List.of(
                new AuthorDto("1", "Author_1"),
                new AuthorDto("2", "Author_2"),
                new AuthorDto("3", "Author_3")
        );
    }
    
    @DisplayName("должен возвращать список всех авторов")
    @Test
    void shouldFindAllAuthors() throws Exception {
        when(authorRepository.findAll()).thenReturn(Flux.fromIterable(authorsDto).map(AuthorDto::toModelObject));

        WebTestClient testClient = WebTestClient.bindToController(authorController).build();
        testClient.get()
                .uri("/api/v1/authors")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(AuthorDto.class)
                .isEqualTo(authorsDto);
        verify(authorRepository, times(1)).findAll();
    }
}
