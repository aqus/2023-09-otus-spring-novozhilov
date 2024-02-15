package ru.otus.catalog.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.catalog.dto.AuthorDto;
import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.dto.CreateBookDto;
import ru.otus.catalog.dto.GenreDto;
import ru.otus.catalog.dto.UpdateBookDto;
import ru.otus.catalog.models.Book;
import ru.otus.catalog.repositories.AuthorRepository;
import ru.otus.catalog.repositories.BookRepository;
import ru.otus.catalog.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@DisplayName("Контроллер книг")
@SpringBootTest(classes = {BookController.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookControllerTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private BookController bookController;
    
    private List<BookDto> books;
    
    @BeforeEach
    void setUp() {
        books = List.of(
                new BookDto(1, "BookTitle_1", new AuthorDto(1, "Author_1"),
                        List.of(new GenreDto(1, "Genre_1"), new GenreDto(2, "Genre_2"))
                ),
                new BookDto(2, "BookTitle_2", new AuthorDto(2, "Author_2"),
                        List.of(new GenreDto(3, "Genre_3"), new GenreDto(4, "Genre_4"))
                ),
                new BookDto(3, "BookTitle_3", new AuthorDto(3, "Author_3"),
                        List.of(new GenreDto(5, "Genre_5"), new GenreDto(6, "Genre_6"))
                )
        );
    }
    
    @DisplayName("должен возвращать все книги")
    @Test
    void shouldFindAllBooks() {
        when(bookRepository.findAll()).thenReturn(Flux.fromIterable(books).map(BookDto::toModelObject));

        WebTestClient testClient = WebTestClient.bindToController(bookController).build();
        testClient.get()
                .uri("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(BookDto.class)
                .isEqualTo(books);

        verify(bookRepository, times((1))).findAll();
    }
    
    @DisplayName("должен возвращать книгу по id")
    @Test
    void shouldFindBookById() {
        BookDto bookDto = books.get(0);
        when(bookRepository.findById(anyLong())).thenReturn(Mono.just(bookDto.toModelObject()));

        WebTestClient testClient = WebTestClient.bindToController(bookController).build();
        testClient.get()
                .uri("/api/v1/books/{id}", bookDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookDto.class)
                .isEqualTo(bookDto);

        verify(bookRepository, times(1)).findById(anyLong());
    }
    
    @DisplayName("должен добавлять новую книгу")
    @Test
    void shouldCreateNewBook() {
        BookDto expectedNewBook = new BookDto(4, "NewBookTitle_4", new AuthorDto(2, "Author_2"),
                List.of(new GenreDto(2, "Genre_2"), new GenreDto(5, "Genre_5"))
        );
        when(bookRepository.save(any(Book.class))).thenReturn(
                Mono.just(expectedNewBook.toModelObject()));
        when(authorRepository.findById(anyLong())).thenReturn(
                Mono.just(expectedNewBook.getAuthorDto().toModelObject()));
        when(genreRepository.findAllById(anyList()))
                .thenReturn(Flux.just(new GenreDto(2, "Genre_2").toModelObject(),
                        new GenreDto(5, "Genre_5").toModelObject()));

        CreateBookDto createBookDto = new CreateBookDto("NewBookTitle_4", 2L, List.of(2L, 5L));
        WebTestClient testClient = WebTestClient.bindToController(bookController).build();
        testClient.post()
                .uri("/api/v1/books")
                .body(BodyInserters.fromValue(createBookDto))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookDto.class)
                .isEqualTo(expectedNewBook);

        verify(bookRepository, times(1)).save(any(Book.class));
        verify(authorRepository, times(1)).findById(anyLong());
        verify(genreRepository, times(1)).findAllById(anyList());
    }
    
    @DisplayName("должен обновлять книгу")
    @Test
    void shouldUpdateBook() {
        BookDto actualBookDto = new BookDto(2, "BookTitle_2", new AuthorDto(2, "Author_2"),
                List.of(new GenreDto(3, "Genre_3"), new GenreDto(4, "Genre_4")));
        UpdateBookDto updateBookDto = new UpdateBookDto(2L, "UpdatedBookTitle_2", 2L, List.of(3L,4L));
        BookDto expectedBookDto = new BookDto(updateBookDto.getId(), updateBookDto.getTitle(),
                new AuthorDto(2, "Author_2"),
                List.of(new GenreDto(3, "Genre_3"), new GenreDto(4, "Genre_4")));

        when(bookRepository.findById(anyLong())).thenReturn(Mono.just(actualBookDto.toModelObject()));
        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(expectedBookDto.toModelObject()));
        when(authorRepository.findById(anyLong())).thenReturn(
                Mono.just(expectedBookDto.getAuthorDto().toModelObject()));
        when(genreRepository.findAllById(anyList()))
                .thenReturn(Flux.just(new GenreDto(3, "Genre_3").toModelObject(),
                        new GenreDto(4, "Genre_4").toModelObject()));

        WebTestClient testClient = WebTestClient.bindToController(bookController).build();
        testClient.put()
                .uri("/api/v1/books")
                .body(BodyInserters.fromValue(updateBookDto))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookDto.class)
                .isEqualTo(expectedBookDto);

        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(authorRepository, times(1)).findById(anyLong());
        verify(genreRepository, times(1)).findAllById(anyList());
    }
    
    @DisplayName("должен удалять книгу по id")
    @Test
    void shouldDeleteBook() throws Exception {
        BookDto bookDto = books.get(0);
        doReturn(Mono.empty()).when(bookRepository).deleteById(anyLong());

        WebTestClient testClient = WebTestClient.bindToController(bookController).build();
        testClient.delete()
                .uri("/api/v1/books/{id}", bookDto.getId())
                .exchange()
                .expectStatus()
                .isOk();

        verify(bookRepository, times(1)).deleteById(anyLong());
    }
}
