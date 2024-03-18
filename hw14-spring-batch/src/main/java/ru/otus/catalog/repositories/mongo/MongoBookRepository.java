package ru.otus.catalog.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.catalog.models.nosql.MongoBook;

public interface MongoBookRepository extends MongoRepository<MongoBook, String> {
}
