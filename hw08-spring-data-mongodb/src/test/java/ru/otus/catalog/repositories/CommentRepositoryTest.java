package ru.otus.catalog.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.catalog.models.Book;
import ru.otus.catalog.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Mongo репозиторий комментариев к книгам")
@DataMongoTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @DisplayName("должен загружать комментарии книги")
    @Test
    void shouldLoadCommentsByBookId() {
        Query bookQuery = new Query();
        bookQuery.addCriteria(Criteria.where("title").is("BookTitle_1"));
        Book book = mongoOperations.findOne(bookQuery, Book.class);
        List<Comment> actualComments = commentRepository.findAllByBookId(book.getId());

        assertThat(actualComments.get(0).getText()).isEqualTo("BookComment_1");
        assertThat(actualComments.size()).isEqualTo(1);
        assertThat(actualComments.get(0).getClass()).isEqualTo(Comment.class);
    }
}
