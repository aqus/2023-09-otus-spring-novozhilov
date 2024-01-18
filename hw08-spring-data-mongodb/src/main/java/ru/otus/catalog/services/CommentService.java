package ru.otus.catalog.services;

import ru.otus.catalog.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto findById(String id);

    List<CommentDto> findAllByBookId(String id);

    CommentDto create(CommentDto book);

    CommentDto update(CommentDto book);

    void deleteById(String id);
}
