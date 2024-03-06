package ru.otus.catalog.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.otus.catalog.dto.GenreDto;
import ru.otus.catalog.security.JwtAuthenticationFilter;
import ru.otus.catalog.security.UserService;
import ru.otus.catalog.services.GenreService;

import java.util.List;

@DisplayName("Контроллер жанров")
@WebMvcTest(value = GenreController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = JwtAuthenticationFilter.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private GenreService genreService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    
    private List<GenreDto> genres;
    
    @BeforeEach
    void setUp() {
        genres = List.of(
                new GenreDto(1, "Genre_1"),
                new GenreDto(2, "Genre_2"),
                new GenreDto(3, "Genre_3"),
                new GenreDto(4, "Genre_4"),
                new GenreDto(5, "Genre_5"),
                new GenreDto(6, "Genre_6")
        );
    }
    
    @DisplayName("должен возвращать список жанров")
    @Test
    void shouldReturnAllTheGenres() throws Exception {
        when(genreService.findAll()).thenReturn(genres);
        
        mockMvc.perform(get("/api/v1/genres"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(genres)));
        
        verify(genreService, times(1)).findAll();
    }
}
