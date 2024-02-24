package ru.otus.catalog.repositories;

import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.otus.catalog.models.Comment;

@Repository
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final ReactiveMongoOperations mongoOperations;

    public CommentCustomRepositoryImpl(ReactiveMongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Mono<DeleteResult> deleteCommentsByBook(String bookId) {
        Query query = new Query(Criteria.where("book").is(bookId));
        return mongoOperations.remove(query, Comment.class, "comments");
    }
}
