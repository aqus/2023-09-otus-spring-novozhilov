package ru.otus.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.catalog.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
