package ru.otus.catalog.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.catalog.models.Book;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {

}
