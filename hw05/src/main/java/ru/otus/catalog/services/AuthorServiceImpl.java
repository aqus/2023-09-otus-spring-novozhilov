package ru.otus.catalog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.otus.catalog.models.Author;
import ru.otus.catalog.repositories.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {
    
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
