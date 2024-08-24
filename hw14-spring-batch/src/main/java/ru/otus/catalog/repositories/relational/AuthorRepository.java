package ru.otus.catalog.repositories.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.catalog.models.relational.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
