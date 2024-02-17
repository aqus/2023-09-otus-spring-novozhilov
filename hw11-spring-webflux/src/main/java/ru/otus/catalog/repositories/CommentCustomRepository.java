package ru.otus.catalog.repositories;

import com.mongodb.client.result.DeleteResult;

public interface CommentCustomRepository {

    DeleteResult deleteCommentsByBook(String bookId);
}
