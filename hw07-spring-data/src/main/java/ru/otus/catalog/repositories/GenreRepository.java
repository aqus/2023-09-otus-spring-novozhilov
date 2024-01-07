package ru.otus.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.catalog.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
