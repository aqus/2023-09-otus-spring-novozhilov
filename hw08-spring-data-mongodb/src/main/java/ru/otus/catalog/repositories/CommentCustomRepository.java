package ru.otus.catalog.repositories;

public interface CommentCustomRepository {

    void deleteCommentsByBook(String bookId);
}
