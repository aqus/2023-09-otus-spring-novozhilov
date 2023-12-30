package ru.otus.catalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ru.otus.catalog.dto.GenreDto;
import ru.otus.catalog.mappers.GenreMapper;
import ru.otus.catalog.repositories.GenreRepository;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll()
                .stream()
                .map(GenreMapper::toGenreDto)
                .collect(Collectors.toList());
    }
}
