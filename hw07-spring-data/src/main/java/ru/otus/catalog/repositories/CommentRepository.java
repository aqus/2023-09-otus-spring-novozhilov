package ru.otus.catalog.repositories;

import ru.otus.catalog.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(long id);

    List<Comment> findAllByBookId(long id);

    Comment save(Comment comment);

    void deleteById(long id);
}
