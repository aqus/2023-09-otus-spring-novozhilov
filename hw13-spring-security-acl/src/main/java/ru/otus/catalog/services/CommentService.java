package ru.otus.catalog.services;

import ru.otus.catalog.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto findById(long id);

    List<CommentDto> findAllByBookId(long id);

    CommentDto create(CommentDto book);

    CommentDto update(CommentDto book);

    void deleteById(long id);
}
