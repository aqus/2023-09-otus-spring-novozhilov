package ru.otus.catalog.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
    private ReactiveMongoOperations mongoOperations;

    @DisplayName("должен удалять комментарии книги")
    @Test
    void shouldDeleteBookComment() {
        Query bookQuery = new Query();
        bookQuery.addCriteria(Criteria.where("title").is("BookTitle_1"));
        Mono<Book> book = mongoOperations.findOne(bookQuery, Book.class);

        List<Comment> comments = book
                .map(book1 -> new Query(Criteria.where("book").is(book1.getId())))
                .map(query -> mongoOperations.find(query, Comment.class))
                .flatMap(Flux::collectList)
                .block();

        assertThat(comments.get(0).getText()).isEqualTo("BookComment_1");

        book.flatMap(book1 -> commentCustomRepository.deleteCommentsByBook(book1.getId())).block();

        List<Comment> afterDeleteComments = book
                .map(book1 -> new Query(Criteria.where("book").is(book1.getId())))
                .map(query -> mongoOperations.find(query, Comment.class))
                .flatMap(Flux::collectList)
                .block();

        assertThat(afterDeleteComments).isEmpty();
    }
}