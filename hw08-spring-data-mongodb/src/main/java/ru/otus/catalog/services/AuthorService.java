package ru.otus.catalog.services;

import java.util.List;

import ru.otus.catalog.dto.AuthorDto;

public interface AuthorService {

    List<AuthorDto> findAll();
}
