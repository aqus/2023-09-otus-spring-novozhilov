package ru.otus.catalog.repositories;

import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ru.otus.catalog.models.Comment;

@Repository
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final MongoOperations mongoOperations;

    public CommentCustomRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public DeleteResult deleteCommentsByBook(String bookId) {
        Query query = new Query(Criteria.where("book").is(bookId));
        return mongoOperations.remove(query, Comment.class, "comments");
    }
}
