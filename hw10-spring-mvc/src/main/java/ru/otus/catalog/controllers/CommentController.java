package ru.otus.catalog.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.catalog.dto.CommentDto;
import ru.otus.catalog.dto.CreateCommentDto;
import ru.otus.catalog.dto.UpdateCommentDto;
import ru.otus.catalog.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments/{bookId}")
    public List<CommentDto> findAllCommentsByBookId(@PathVariable long bookId) {
        return commentService.findAllByBookId(bookId);
    }

    @GetMapping("/comment/{id}")
    public CommentDto findCommentById(@PathVariable long id) {
        return commentService.findById(id);
    }

    @PostMapping("/comments")
    public CommentDto insertComment(@RequestBody @Valid CreateCommentDto createCommentDto) {
        return commentService.create(
                new CommentDto(
                        null,
                        createCommentDto.getText(),
                        createCommentDto.getBookId()));
    }

    @PutMapping("/comments")
    public CommentDto updateBook(@RequestBody @Valid UpdateCommentDto updateCommentDto) {
        return commentService.update(
                new CommentDto(
                        updateCommentDto.getId(),
                        updateCommentDto.getText(),
                        updateCommentDto.getBookId()));
    }

    @DeleteMapping("/comments/{id}")
    public void deleteBook(@PathVariable long id) {
        commentService.deleteById(id);
    }
}
