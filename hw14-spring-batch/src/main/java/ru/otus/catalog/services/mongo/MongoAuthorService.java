package ru.otus.catalog.services.mongo;

import org.springframework.stereotype.Service;
import ru.otus.catalog.models.nosql.MongoAuthor;
import ru.otus.catalog.models.relational.Batch;
import ru.otus.catalog.repositories.mongo.MongoAuthorRepository;
import ru.otus.catalog.repositories.relational.BatchRepository;

import javax.annotation.Nullable;
import java.util.Optional;

import static ru.otus.catalog.configs.AuthorConfigJob.IMPORT_AUTHOR_JOB_NAME;

@Service
public class MongoAuthorService {

    private final MongoAuthorRepository mongoAuthorRepository;

    private final BatchRepository batchRepository;

    public MongoAuthorService(MongoAuthorRepository mongoAuthorRepository, BatchRepository batchRepository) {
        this.mongoAuthorRepository = mongoAuthorRepository;
        this.batchRepository = batchRepository;
    }

    @Nullable
    public MongoAuthor findById(String id) {
        Optional<Batch> optionalBatch = batchRepository.findByImportLink(IMPORT_AUTHOR_JOB_NAME, id);
        MongoAuthor author = null;

        if (optionalBatch.isPresent()) {
            String mongoId = optionalBatch.get().getExportLink();
            Optional<MongoAuthor> optionalMongoAuthor = mongoAuthorRepository.findById(mongoId);
            author = optionalMongoAuthor.orElse(null);
        }

        return author;
    }
}
