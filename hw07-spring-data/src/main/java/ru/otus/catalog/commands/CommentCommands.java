package ru.otus.catalog.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.catalog.converters.CommentConverter;
import ru.otus.catalog.dto.CommentDto;
import ru.otus.catalog.services.CommentService;

import java.util.stream.Collectors;

@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    public CommentCommands(CommentService commentService, CommentConverter commentConverter) {
        this.commentService = commentService;
        this.commentConverter = commentConverter;
    }

    @ShellMethod(value = "Find all comments by book id", key = "ac")
    public String findAllCommentsByBookId(long bookId) {
        return commentService.findAllByBookId(bookId)
                .stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findCommentById(long id) {
        return commentConverter.commentToString(commentService.findById(id));
    }

    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(String text, long bookId) {
        var savedComment = commentService.create(
                new CommentDto(
                        null,
                        text,
                        bookId));

        return commentConverter.commentToString(savedComment);
    }

    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateBook(long id, String text, long bookId) {
        var savedComment = commentService.update(
                new CommentDto(
                        id,
                        text,
                        bookId));

        return commentConverter.commentToString(savedComment);
    }

    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteBook(long id) {
        commentService.deleteById(id);
    }
}
