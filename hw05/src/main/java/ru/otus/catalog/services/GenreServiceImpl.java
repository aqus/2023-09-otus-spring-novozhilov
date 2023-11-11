package ru.otus.catalog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.catalog.models.Genre;
import ru.otus.catalog.repositories.GenreRepository;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }
}
