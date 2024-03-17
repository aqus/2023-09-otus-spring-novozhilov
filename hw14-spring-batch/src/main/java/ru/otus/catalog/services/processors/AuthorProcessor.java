package ru.otus.catalog.services.processors;

import org.springframework.batch.item.ItemProcessor;
import ru.otus.catalog.models.nosql.MongoAuthor;
import ru.otus.catalog.models.relational.Author;
import ru.otus.catalog.services.BatchService;

import static ru.otus.catalog.configs.AuthorConfigJob.IMPORT_AUTHOR_JOB_NAME;

public class AuthorProcessor implements ItemProcessor<Author, MongoAuthor> {

    private final BatchService batchService;

    public AuthorProcessor(BatchService batchService) {
        this.batchService = batchService;
    }

    @Override
    public MongoAuthor process(Author author) throws Exception {
        MongoAuthor mongoAuthor = new MongoAuthor(author.getFullName());
        batchService.insert(IMPORT_AUTHOR_JOB_NAME, String.valueOf(author.getId()), mongoAuthor.getId());
        return mongoAuthor;
    }
}
