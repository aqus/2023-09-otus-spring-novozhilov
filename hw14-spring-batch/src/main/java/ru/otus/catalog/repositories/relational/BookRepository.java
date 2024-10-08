package ru.otus.catalog.repositories.relational;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.catalog.models.relational.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph("book-entity-graph")
    Optional<Book> findById(long id);

    @EntityGraph("book-entity-graph")
    List<Book> findAll();
}
