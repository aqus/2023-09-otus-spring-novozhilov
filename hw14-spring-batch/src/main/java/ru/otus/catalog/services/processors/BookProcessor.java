package ru.otus.catalog.services.processors;

import org.springframework.batch.item.ItemProcessor;
import ru.otus.catalog.models.nosql.MongoBook;
import ru.otus.catalog.models.relational.Book;
import ru.otus.catalog.services.mongo.MongoBookService;

public class BookProcessor implements ItemProcessor<Book, MongoBook> {

    private final MongoBookService mongoBookService;

    public BookProcessor(MongoBookService mongoBookService) {
        this.mongoBookService = mongoBookService;
    }

    @Override
    public MongoBook process(Book book) throws Exception {
        return mongoBookService.getMongoBook(book);
    }
}
