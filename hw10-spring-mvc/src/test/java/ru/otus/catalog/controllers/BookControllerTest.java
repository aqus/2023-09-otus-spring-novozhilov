package ru.otus.catalog.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.otus.catalog.dto.AuthorDto;
import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.dto.GenreDto;
import ru.otus.catalog.services.BookService;

import java.util.List;

@DisplayName("Контроллер книг")
@WebMvcTest(BookController.class)
class BookControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BookService bookService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
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
    void shouldFindAllBooks() throws Exception {
        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(books)));

        verify(bookService, times((1))).findAll();
    }

}
