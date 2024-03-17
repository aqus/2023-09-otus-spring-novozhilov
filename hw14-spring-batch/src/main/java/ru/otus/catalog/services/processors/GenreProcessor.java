package ru.otus.catalog.services.processors;

import org.springframework.batch.item.ItemProcessor;
import ru.otus.catalog.models.nosql.MongoGenre;
import ru.otus.catalog.models.relational.Genre;
import ru.otus.catalog.services.BatchService;

import static ru.otus.catalog.configs.GenreConfigJob.IMPORT_GENRE_JOB_NAME;

public class GenreProcessor implements ItemProcessor<Genre, MongoGenre> {

    private final BatchService batchService;

    public GenreProcessor(BatchService batchService) {
        this.batchService = batchService;
    }

    @Override
    public MongoGenre process(Genre genre) {
        MongoGenre mongoGenre = new MongoGenre(genre.getName());
        batchService.insert(IMPORT_GENRE_JOB_NAME, String.valueOf(genre.getId()), mongoGenre.getId());
        return mongoGenre;
    }
}
