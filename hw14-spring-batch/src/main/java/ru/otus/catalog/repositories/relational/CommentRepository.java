package ru.otus.catalog.repositories.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.catalog.models.relational.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBookId(long id);
}
