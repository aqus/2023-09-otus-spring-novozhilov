package ru.otus.catalog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.catalog.models.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {

}
