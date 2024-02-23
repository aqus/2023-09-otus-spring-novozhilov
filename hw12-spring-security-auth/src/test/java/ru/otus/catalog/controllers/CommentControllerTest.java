package ru.otus.catalog.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.otus.catalog.dto.CommentDto;
import ru.otus.catalog.dto.CreateCommentDto;
import ru.otus.catalog.dto.UpdateCommentDto;
import ru.otus.catalog.security.UserService;
import ru.otus.catalog.services.CommentService;

import java.util.List;

@DisplayName("Контроллер комментариев")
@WebMvcTest(value = CommentController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CommentService commentService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    
    private List<CommentDto> comments;
    
    @BeforeEach
    void setUp() {
        comments = List.of(
                new CommentDto(1L, "BookComment_1", 1L),
                new CommentDto(2L, "BookComment_2", 2L),
                new CommentDto(3L, "BookComment_3", 3L)
        );
    }
    
    @DisplayName("должен возвращать комментарий по id книги")
    @Test
    void shouldFindAllCommentsByBookId() throws Exception {
        CommentDto comment = comments.get(0);
        List<CommentDto> expectedComments = List.of(comment);
        long bookId = comment.getBookId();
        when(commentService.findAllByBookId(bookId)).thenReturn(expectedComments);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/comments/" + bookId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedComments)));
        
        verify(commentService, times(1)).findAllByBookId(bookId);
    }
    
    
    @DisplayName("должен возвращать комментарий по id")
    @Test
    void shouldFindCommentById() throws Exception {
        CommentDto comment = comments.get(0);
        when(commentService.findById(anyLong())).thenReturn(comment);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/comment/" + comment.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(comment)));

        verify(commentService, times(1)).findById(comment.getId());
    }
    
    @DisplayName("должен создавать новый комментарий")
    @Test
    void shouldCreateNewComment() throws Exception {
        CommentDto expectedCommentDto = new CommentDto(4L, "NewBookComment", 2L);
        CreateCommentDto newBookComment = new CreateCommentDto(expectedCommentDto.getText(),
                expectedCommentDto.getBookId());
        when(commentService.create(any())).thenReturn(expectedCommentDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBookComment)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCommentDto)));
        
        verify(commentService, times(1)).create(any());
    }
    
    @DisplayName("должен обновлять комментарий")
    @Test
    void shouldUpdateBook() throws Exception {
        CommentDto commentDto = comments.get(0);
        UpdateCommentDto updatedBookComment = new UpdateCommentDto(commentDto.getId(), "UpdatedBookComment_1",
                commentDto.getBookId());
        CommentDto expectedCommentDto = new CommentDto(updatedBookComment.getId(), updatedBookComment.getText(),
                updatedBookComment.getBookId());
        when(commentService.update(any())).thenReturn(expectedCommentDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBookComment)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCommentDto)));

        verify(commentService, times(1)).update(any());
    }
    
    
    @DisplayName("должен удалять комментарий по id")
    @Test
    void shouldDeleteComment() throws Exception {
        CommentDto expectedComment = comments.get(0);
        doNothing().when(commentService).deleteById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/comments/" + expectedComment.getId()))
                .andExpect(status().isOk());

        verify(commentService, times(1)).deleteById(anyLong());
    }
}
