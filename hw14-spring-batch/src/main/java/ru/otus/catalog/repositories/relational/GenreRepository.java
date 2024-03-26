package ru.otus.catalog.repositories.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.catalog.models.relational.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
