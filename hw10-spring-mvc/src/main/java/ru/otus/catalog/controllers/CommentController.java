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
import ru.otus.catalog.converters.CommentConverter;
import ru.otus.catalog.dto.CommentDto;
import ru.otus.catalog.dto.CreateCommentDto;
import ru.otus.catalog.dto.UpdateCommentDto;
import ru.otus.catalog.services.CommentService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/")
public class CommentController {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    public CommentController(CommentService commentService, CommentConverter commentConverter) {
        this.commentService = commentService;
        this.commentConverter = commentConverter;
    }

    @GetMapping("/comments/{bookId}")
    public String findAllCommentsByBookId(@PathVariable long bookId) {
        return commentService.findAllByBookId(bookId)
                .stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @GetMapping("/comments/{id}")
    public String findCommentById(@PathVariable long id) {
        return commentConverter.commentToString(commentService.findById(id));
    }

    @PostMapping("/comments")
    public String insertComment(@RequestBody @Valid CreateCommentDto createCommentDto) {
        var savedComment = commentService.create(
                new CommentDto(
                        null,
                        createCommentDto.getText(),
                        createCommentDto.getBookId()));

        return commentConverter.commentToString(savedComment);
    }

    @PutMapping("/comments")
    public String updateBook(@RequestBody @Valid UpdateCommentDto updateCommentDto) {
        var savedComment = commentService.update(
                new CommentDto(
                        updateCommentDto.getId(),
                        updateCommentDto.getText(),
                        updateCommentDto.getBookId()));

        return commentConverter.commentToString(savedComment);
    }

    @DeleteMapping("/comments/{id}")
    public void deleteBook(@PathVariable long id) {
        commentService.deleteById(id);
    }
}
