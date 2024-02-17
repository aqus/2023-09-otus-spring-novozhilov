package ru.otus.catalog.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.catalog.models.Book;
import ru.otus.catalog.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Кастомный Mongo репозиторий комментариев к книгам")
@DataMongoTest
@Import({CommentCustomRepositoryImpl.class})
class CommentCustomRepositoryTest {

    @Autowired
    private CommentCustomRepository commentCustomRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @DisplayName("должен удалять комментарии книги")
    @Test
    void shouldDeleteBookComment() {
        Query bookQuery = new Query();
        bookQuery.addCriteria(Criteria.where("title").is("BookTitle_1"));
        Book book = mongoOperations.findOne(bookQuery, Book.class);

        String bookId = book.getId();

        Query commentsQuery = new Query(Criteria.where("book").is(bookId));
        List<Comment> comments = mongoOperations.find(commentsQuery, Comment.class);
        assertThat(comments.get(0).getText()).isEqualTo("BookComment_1");

        commentCustomRepository.deleteCommentsByBook(bookId);

        Query afterDeleteQuery = new Query(Criteria.where("book").is(bookId));
        List<Comment> afterDeleteComments = mongoOperations.find(afterDeleteQuery, Comment.class);
        assertThat(afterDeleteComments).isEmpty();
    }
}