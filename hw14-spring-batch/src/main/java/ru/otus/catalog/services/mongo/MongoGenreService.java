package ru.otus.catalog.services.mongo;

import org.springframework.stereotype.Service;
import ru.otus.catalog.models.nosql.MongoGenre;
import ru.otus.catalog.models.relational.Batch;
import ru.otus.catalog.repositories.mongo.MongoGenreRepository;
import ru.otus.catalog.repositories.relational.BatchRepository;

import javax.annotation.Nullable;
import java.util.Optional;

import static ru.otus.catalog.configs.GenreConfigJob.IMPORT_GENRE_JOB_NAME;

@Service
public class MongoGenreService {

    private final MongoGenreRepository mongoGenreRepository;

    private final BatchRepository batchRepository;

    public MongoGenreService(MongoGenreRepository mongoGenreRepository, BatchRepository batchRepository) {
        this.mongoGenreRepository = mongoGenreRepository;
        this.batchRepository = batchRepository;
    }

    @Nullable
    public MongoGenre findById(String id) {
        Optional<Batch> optionalBatch = batchRepository.findByImportLink(IMPORT_GENRE_JOB_NAME, id);
        MongoGenre genre = null;

        if (optionalBatch.isPresent()) {
            Optional<MongoGenre> oGenre = mongoGenreRepository.findById(optionalBatch.get().getExportLink());
            genre = oGenre.orElse(null);
        }

        return genre;
    }
}
