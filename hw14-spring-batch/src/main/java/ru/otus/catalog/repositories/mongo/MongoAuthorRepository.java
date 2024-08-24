package ru.otus.catalog.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.catalog.models.nosql.MongoAuthor;

public interface MongoAuthorRepository extends MongoRepository<MongoAuthor, String> {
}
