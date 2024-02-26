package ru.otus.catalog.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.catalog.models.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

}
