package ru.otus.catalog.services.processors;

import org.springframework.batch.item.ItemProcessor;
import ru.otus.catalog.models.nosql.MongoAuthor;
import ru.otus.catalog.models.relational.Author;

public class AuthorProcessor implements ItemProcessor<Author, MongoAuthor> {

    public AuthorProcessor() {
    }

    @Override
    public MongoAuthor process(Author author) throws Exception {
        return new MongoAuthor(author.getFullName());
    }
}
