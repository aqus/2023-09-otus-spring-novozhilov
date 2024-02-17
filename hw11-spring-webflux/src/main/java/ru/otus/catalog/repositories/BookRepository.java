package ru.otus.catalog.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.catalog.models.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

}
