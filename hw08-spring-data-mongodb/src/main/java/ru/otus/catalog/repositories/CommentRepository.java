package ru.otus.catalog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.catalog.models.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByBookId(String id);
}
