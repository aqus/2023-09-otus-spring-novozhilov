package ru.otus.catalog.repositories;

import com.mongodb.client.result.DeleteResult;
import reactor.core.publisher.Mono;

public interface CommentCustomRepository {

    Mono<DeleteResult> deleteCommentsByBook(String bookId);
}
