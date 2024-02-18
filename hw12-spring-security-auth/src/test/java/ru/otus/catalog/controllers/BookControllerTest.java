package ru.otus.catalog.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.otus.catalog.dto.AuthorDto;
import ru.otus.catalog.dto.BookDto;
import ru.otus.catalog.dto.CreateBookDto;
import ru.otus.catalog.dto.GenreDto;
import ru.otus.catalog.dto.UpdateBookDto;
import ru.otus.catalog.security.SecurityConfiguration;
import ru.otus.catalog.security.UserService;
import ru.otus.catalog.services.BookService;

import java.util.List;

@DisplayName("Контроллер книг")
@WebMvcTest(BookController.class)
@Import(SecurityConfiguration.class)
@WithMockUser(username = "user")
class BookControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BookService bookService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    
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
    
    @DisplayName("должен возвращать книгу по id")
    @Test
    void shouldFindBookById() throws Exception {
        BookDto bookDto = books.get(0);
        when(bookService.findById(anyLong())).thenReturn(bookDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/{id}", bookDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(bookDto)));

        verify(bookService, times(1)).findById(anyLong());
    }
    
    @DisplayName("должен добавлять новую книгу")
    @Test
    void shouldCreateNewBook() throws Exception {
        BookDto expectedNewBook = new BookDto(4, "NewBookTitle_4", new AuthorDto(2, "Author_2"),
                List.of(new GenreDto(2, "Genre_2"), new GenreDto(5, "Genre_5"))
        );
        when(bookService.insert(anyString(), anyLong(), any())).thenReturn(expectedNewBook);

        CreateBookDto createBookDto = new CreateBookDto("NewBookTitle_4", 2L, List.of(2L, 5L));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedNewBook)));

        verify(bookService, times(1)).insert(anyString(), anyLong(), any());
    }
    
    @DisplayName("должен обновлять книгу")
    @Test
    void shouldUpdateBook() throws Exception {
        UpdateBookDto updateBookDto = new UpdateBookDto(2L, "UpdatedBookTitle_2", 2L, List.of(3L,4L));
        BookDto expectedBookDto = new BookDto(updateBookDto.getId(), updateBookDto.getTitle(),
                new AuthorDto(2, "Author_2"),
                List.of(new GenreDto(3, "Genre_3"), new GenreDto(4, "Genre_4")));
        
        when(bookService.update(anyLong(), anyString(), anyLong(), any())).thenReturn(expectedBookDto);
        
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateBookDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBookDto)));

        verify(bookService, times(1)).update(anyLong(), anyString(), anyLong(), any());
    }
    
    @DisplayName("должен удалять книгу по id")
    @Test
    void shouldDeleteBook() throws Exception {
        BookDto bookDto = books.get(0);
        doNothing().when(bookService).deleteById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/books/{id}", bookDto.getId()))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteById(anyLong());
    }
}
