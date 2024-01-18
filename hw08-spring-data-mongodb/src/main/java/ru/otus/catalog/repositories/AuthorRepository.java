package ru.otus.catalog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.catalog.models.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

}
