package ru.otus.catalog.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.otus.catalog.security.SecurityConfiguration;
import ru.otus.catalog.security.UserService;
import ru.otus.catalog.services.AuthorService;
import ru.otus.catalog.services.BookService;
import ru.otus.catalog.services.CommentService;
import ru.otus.catalog.services.GenreService;

import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Проверяет доступность контроллеров")
@WebMvcTest(controllers = {AuthorController.class, BookController.class, CommentController.class,
        GenreController.class})
@Import(SecurityConfiguration.class)
class ControllersEndpointsSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private UserService userService;

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private GenreService genreService;

    @ParameterizedTest(name = "{index} - {0}")
    @ArgumentsSource(TestArgumentsProvider.class)
    void shouldCheckEndpointAvailability(String name, RequestPostProcessor user, MockHttpServletRequestBuilder method,
                                         ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(method.with(user).with(csrf())).andExpect(resultMatcher);
    }

    static class TestArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of("auth get authors", user("user"),
                            get("/api/v1/authors"), status().isOk()),

                    Arguments.of("anonymous get authors", anonymous(),
                            get("/api/v1/authors"), status().isUnauthorized()),

                    Arguments.of("auth get book by id", user("user"),
                            get("/api/v1/books/{id}", 0L), status().isOk()),

                    Arguments.of("anonymous get book by id", anonymous(),
                            get("/api/v1/books/{id}", 0L), status().isUnauthorized()),

                    Arguments.of("auth get books", user("user"),
                            get("/api/v1/books"), status().isOk()),

                    Arguments.of("anonymous get books", anonymous(),
                            get("/api/v1/books"), status().isUnauthorized()),

                    Arguments.of("auth add book", user("user"),
                            post("/api/v1/books").contentType(MediaType.APPLICATION_JSON)
                                    .content(""), status().isBadRequest()),

                    Arguments.of("anonymous add book", anonymous(),
                            post("/api/v1/books").contentType(MediaType.APPLICATION_JSON)
                                    .content(""), status().isUnauthorized()),

                    Arguments.of("auth update book", user("user"),
                            put("/api/v1/books").contentType(MediaType.APPLICATION_JSON)
                                    .content(""), status().isBadRequest()),

                    Arguments.of("anonymous update book", anonymous(),
                            put("/api/v1/books").contentType(MediaType.APPLICATION_JSON)
                                    .content(""), status().isUnauthorized()),

                    Arguments.of("auth delete book", user("user"),
                            delete("/api/v1/books/{id}", 0L), status().isOk()),

                    Arguments.of("anonymous delete book", anonymous(),
                            delete("/api/v1/books/{id}", 0L), status().isUnauthorized()),

                    Arguments.of("auth comments by book id", user("user"),
                            get("/api/v1/comments/{id}", 0L), status().isOk()),

                    Arguments.of("anonymous comments by book id", anonymous(),
                            get("/api/v1/comments/{id}", 0L), status().isUnauthorized()),

                    Arguments.of("auth comment by id", user("user"),
                            get("/api/v1/comment/{id}", 0L), status().isOk()),

                    Arguments.of("anonymous comment by id", anonymous(),
                            get("/api/v1/comment/{id}", 0L), status().isUnauthorized()),

                    Arguments.of("auth add comment", user("user"),
                            post("/api/v1/comments").contentType(MediaType.APPLICATION_JSON).content(""),
                            status().isBadRequest()),

                    Arguments.of("anonymous add comment", anonymous(),
                            post("/api/v1/comments").contentType(MediaType.APPLICATION_JSON).content(""),
                            status().isUnauthorized()),

                    Arguments.of("auth update comment", user("user"),
                            put("/api/v1/comments").contentType(MediaType.APPLICATION_JSON).content(""),
                            status().isBadRequest()),

                    Arguments.of("anonymous update comment", anonymous(),
                            put("/api/v1/comments").contentType(MediaType.APPLICATION_JSON).content(""),
                            status().isUnauthorized()),

                    Arguments.of("auth delete comment", user("user"),
                            delete("/api/v1/comments/{id}", 0L), status().isOk()),

                    Arguments.of("anonymous delete comment", anonymous(),
                            delete("/api/v1/comments/{id}", 0L), status().isUnauthorized()),

                    Arguments.of("auth get all genres", user("user"),
                            get("/api/v1/genres"), status().isOk()),

                    Arguments.of("anonymous get all genres", anonymous(),
                            get("/api/v1/genres"), status().isUnauthorized())

            );
        }
    }

}