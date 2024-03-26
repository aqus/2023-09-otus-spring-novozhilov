package ru.otus.catalog.services.processors;

import org.springframework.batch.item.ItemProcessor;
import ru.otus.catalog.models.nosql.MongoGenre;
import ru.otus.catalog.models.relational.Genre;

public class GenreProcessor implements ItemProcessor<Genre, MongoGenre> {

    public GenreProcessor() {
    }

    @Override
    public MongoGenre process(Genre genre) {
        return new MongoGenre(String.valueOf(genre.getId()), genre.getName());
    }
}
