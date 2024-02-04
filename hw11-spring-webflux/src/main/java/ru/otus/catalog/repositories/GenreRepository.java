package ru.otus.catalog.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.catalog.models.Genre;

public interface GenreRepository extends ReactiveCrudRepository<Genre, Long> {

}
