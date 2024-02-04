package ru.otus.catalog.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.catalog.models.Author;

public interface AuthorRepository extends ReactiveCrudRepository<Author, Long> {

}
