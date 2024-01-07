package ru.otus.catalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ru.otus.catalog.dto.AuthorDto;
import ru.otus.catalog.mappers.AuthorMapper;
import ru.otus.catalog.repositories.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {
    
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDto> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorMapper::toAuthorDto)
                .collect(Collectors.toList());
    }
}
