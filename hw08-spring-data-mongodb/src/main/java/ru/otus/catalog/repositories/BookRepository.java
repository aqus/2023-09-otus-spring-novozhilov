package ru.otus.catalog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.catalog.models.Book;

public interface BookRepository extends MongoRepository<Book, String> {

}
