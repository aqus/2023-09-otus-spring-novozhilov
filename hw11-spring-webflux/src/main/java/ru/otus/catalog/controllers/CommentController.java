package ru.otus.catalog.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.catalog.dto.CommentDto;
import ru.otus.catalog.dto.CreateCommentDto;
import ru.otus.catalog.dto.UpdateCommentDto;
import ru.otus.catalog.exceptions.EntityNotFoundException;
import ru.otus.catalog.mappers.CommentMapper;
import ru.otus.catalog.models.Comment;
import ru.otus.catalog.repositories.BookRepository;
import ru.otus.catalog.repositories.CommentRepository;

@RestController
public class CommentController {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    public CommentController(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("api/v1/comments/{bookId}")
    public Flux<CommentDto> findAllCommentsByBookId(@PathVariable long bookId) {
        return commentRepository.findAllByBookId(bookId).map(CommentMapper::toCommentDto);
    }

    @GetMapping("api/v1/comment/{id}")
    public Mono<CommentDto> findCommentById(@PathVariable long id) {
        return commentRepository.findById(id).map(CommentMapper::toCommentDto);
    }

    @PostMapping("api/v1/comments")
    public Mono<CommentDto> insertComment(@RequestBody @Valid CreateCommentDto createCommentDto) {
        CommentDto commentDto = new CommentDto(null, createCommentDto.getText(), createCommentDto.getBookId());
        return bookRepository.findById(commentDto.getBookId())
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Book with id %d not found"
                        .formatted(commentDto.getBookId()))))
                .flatMap(book -> {
                    Comment newComment = new Comment(commentDto.getId(), commentDto.getText(), book);
                    return commentRepository
                            .save(newComment)
                            .map(CommentMapper::toCommentDto);
                });
    }

    @PutMapping("api/v1/comments")
    public Mono<CommentDto> updateComment(@RequestBody @Valid UpdateCommentDto updateCommentDto) {
        CommentDto commentDto = new CommentDto(updateCommentDto.getId(), updateCommentDto.getText(),
                updateCommentDto.getBookId());
        return commentRepository.findById(commentDto.getId())
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Comment with id %d not found"
                        .formatted(commentDto.getId()))))
                .flatMap(comment -> {
                    comment.setText(commentDto.getText());
                    return commentRepository
                            .save(comment)
                            .map(CommentMapper::toCommentDto);
                });
    }

    @DeleteMapping("api/v1/comments/{id}")
    public void deleteComment(@PathVariable long id) {
        commentRepository.deleteById(id);
    }
}
