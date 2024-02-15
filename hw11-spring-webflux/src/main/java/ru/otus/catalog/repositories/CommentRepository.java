package ru.otus.catalog.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.otus.catalog.models.Comment;

public interface CommentRepository extends ReactiveCrudRepository<Comment, Long> {

    Flux<Comment> findAllByBookId(long id);
}
