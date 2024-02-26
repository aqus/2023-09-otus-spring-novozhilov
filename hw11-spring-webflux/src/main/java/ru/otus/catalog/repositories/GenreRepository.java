package ru.otus.catalog.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.catalog.models.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

}
