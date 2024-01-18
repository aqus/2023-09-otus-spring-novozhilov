package ru.otus.catalog.mappers;

import ru.otus.catalog.dto.CommentDto;
import ru.otus.catalog.models.Comment;

public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment) {
        String bookId = comment.getBook() != null ? comment.getBook().getId() : null;
        return new CommentDto(comment.getId(), comment.getText(), bookId);
    }
}
