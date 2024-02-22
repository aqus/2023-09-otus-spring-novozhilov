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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.otus.catalog.dto.AuthorDto;
import ru.otus.catalog.services.AuthorService;

import java.util.List;

@DisplayName("Контроллер авторов")
@WebMvcTest(value = AuthorController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
class AuthorControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<AuthorDto> authorsDto;

    @BeforeEach
    void setUp() {
        authorsDto = List.of(
                new AuthorDto(1, "Author_1"),
                new AuthorDto(2, "Author_2"),
                new AuthorDto(3, "Author_3")
        );
    }
    
    @DisplayName("должен возвращать список всех авторов")
    @Test
    void shouldFindAllAuthors() throws Exception {
        when(authorService.findAll()).thenReturn(authorsDto);

        mockMvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(authorsDto)));

        verify(authorService, times(1)).findAll();
    }
}
