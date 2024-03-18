package ru.otus.catalog.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.catalog.models.nosql.MongoGenre;

public interface MongoGenreRepository extends MongoRepository<MongoGenre, String> {
}
