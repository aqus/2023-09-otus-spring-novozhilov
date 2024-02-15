package ru.otus.catalog.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import ru.otus.catalog.dto.CommentDto;
import ru.otus.catalog.dto.CreateCommentDto;
import ru.otus.catalog.dto.UpdateCommentDto;
import ru.otus.catalog.models.Author;
import ru.otus.catalog.models.Book;
import ru.otus.catalog.models.Comment;
import ru.otus.catalog.models.Genre;
import ru.otus.catalog.repositories.BookRepository;
import ru.otus.catalog.repositories.CommentRepository;

import java.util.List;

@DisplayName("Контроллер комментариев")
@SpringBootTest(classes = { CommentController.class })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommentControllerTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentController commentController;
    
    private List<CommentDto> commentDtos;

    private List<Comment> comments;

    @BeforeEach
    void setUp() {
        commentDtos = List.of(
                new CommentDto(1L, "BookComment_1", 1L),
                new CommentDto(2L, "BookComment_2", 2L),
                new CommentDto(3L, "BookComment_3", 3L)
        );
        comments = List.of(
                new Comment(1L, "BookComment_1", new Book(1, "BookTitle_1",
                        new Author(1, "Author_1"), List.of(new Genre(1, "Genre_1"),
                        new Genre(2, "Genre_2")))),
                new Comment(2L, "BookComment_2", new Book(2, "BookTitle_2",
                        new Author(2, "Author_2"), List.of(new Genre(3, "Genre_3"),
                        new Genre(4, "Genre_4")))),
                new Comment(3L, "BookComment_3", new Book(3, "BookTitle_3",
                        new Author(3, "Author_3"), List.of(new Genre(5, "Genre_5"),
                        new Genre(6, "Genre_6")))),
                new Comment(4L, "NewBookComment", new Book(2, "BookTitle_2",
                        new Author(2, "Author_2"), List.of(new Genre(3, "Genre_3"),
                        new Genre(4, "Genre_4"))))
        );
    }
    
    @DisplayName("должен возвращать комментарий по id книги")
    @Test
    void shouldFindAllCommentsByBookId() throws Exception {
        CommentDto comment = commentDtos.get(0);
        List<CommentDto> expectedComments = List.of(comment);
        long bookId = comment.getBookId();
        when(commentRepository.findAllByBookId(bookId)).thenReturn(Flux.fromArray(new Comment[]{comments.get(0)}));

        WebTestClient testClient = WebTestClient.bindToController(commentController).build();
        testClient.get()
                .uri("/api/v1/comments/{bookId}", bookId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(CommentDto.class)
                .isEqualTo(expectedComments);

        verify(commentRepository, times(1)).findAllByBookId(bookId);
    }
    
    
    @DisplayName("должен возвращать комментарий по id")
    @Test
    void shouldFindCommentById() throws Exception {
        CommentDto comment = commentDtos.get(0);
        when(commentRepository.findById(anyLong())).thenReturn(Mono.just(comments.get(0)));

        WebTestClient testClient = WebTestClient.bindToController(commentController).build();
        testClient.get()
                .uri("/api/v1/comment/{id}",comment.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CommentDto.class)
                .isEqualTo(comment);

        verify(commentRepository, times(1)).findById(comment.getId());
    }
    
    @DisplayName("должен создавать новый комментарий")
    @Test
    void shouldCreateNewComment() throws Exception {
        CommentDto expectedCommentDto = new CommentDto(4L, "NewBookComment", 2L);
        CreateCommentDto newBookComment = new CreateCommentDto(expectedCommentDto.getText(),
                expectedCommentDto.getBookId());
        when(bookRepository.findById(anyLong())).thenReturn(Mono.just(comments.get(3).getBook()));
        when(commentRepository.save(any())).thenReturn(Mono.just(comments.get(3)));

        WebTestClient testClient = WebTestClient.bindToController(commentController).build();
        testClient.post()
                .uri("/api/v1/comments")
                .body(BodyInserters.fromValue(newBookComment))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CommentDto.class)
                .isEqualTo(expectedCommentDto);

        verify(bookRepository, times(1)).findById(anyLong());
        verify(commentRepository, times(1)).save(any());
    }
    
    @DisplayName("должен обновлять комментарий")
    @Test
    void shouldUpdateBook() throws Exception {
        CommentDto commentDto = commentDtos.get(0);
        UpdateCommentDto updatedBookComment = new UpdateCommentDto(commentDto.getId(), "UpdatedBookComment_1",
                commentDto.getBookId());
        CommentDto expectedCommentDto = new CommentDto(updatedBookComment.getId(), updatedBookComment.getText(),
                updatedBookComment.getBookId());
        Comment expectedComment = new Comment(updatedBookComment.getId(), updatedBookComment.getText(), new Book(1, "BookTitle_1",
                new Author(1, "Author_1"), List.of(new Genre(1, "Genre_1"),
                new Genre(2, "Genre_2"))));
        when(commentRepository.findById(anyLong())).thenReturn(Mono.just(expectedComment));
        when(commentRepository.save(any())).thenReturn(Mono.just(expectedComment));

        WebTestClient testClient = WebTestClient.bindToController(commentController).build();
        testClient.put()
                .uri("/api/v1/comments")
                .body(BodyInserters.fromValue(updatedBookComment))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CommentDto.class)
                .isEqualTo(expectedCommentDto);

        verify(commentRepository, times(1)).findById(anyLong());
        verify(commentRepository, times(1)).save(any());
    }
    
    
    @DisplayName("должен удалять комментарий по id")
    @Test
    void shouldDeleteComment() throws Exception {
        CommentDto expectedComment = commentDtos.get(0);
        doReturn(Mono.empty()).when(commentRepository).deleteById(anyLong());

        WebTestClient testClient = WebTestClient.bindToController(commentController).build();
        testClient.delete()
                .uri("/api/v1/comments/{id}", expectedComment.getId())
                .exchange()
                .expectStatus()
                .isOk();

        verify(commentRepository, times(1)).deleteById(anyLong());
    }
}
