package ru.otus.catalog.services.mongo;

import org.springframework.stereotype.Service;
import ru.otus.catalog.models.nosql.MongoAuthor;
import ru.otus.catalog.models.nosql.MongoBook;
import ru.otus.catalog.models.nosql.MongoGenre;
import ru.otus.catalog.models.relational.BatchItem;
import ru.otus.catalog.models.relational.Book;
import ru.otus.catalog.repositories.mongo.MongoBookRepository;
import ru.otus.catalog.repositories.relational.BatchRepository;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.otus.catalog.configs.BookConfigJob.IMPORT_BOOK_JOB_NAME;

@Service
public class MongoBookService {

    private final MongoAuthorService mongoAuthorService;

    private final MongoGenreService mongoGenreService;

    private final MongoBookRepository mongoBookRepository;

    private final BatchRepository batchRepository;

    public MongoBookService(MongoAuthorService mongoAuthorService, MongoGenreService mongoGenreService,
                            MongoBookRepository mongoBookRepository, BatchRepository batchRepository) {
        this.mongoAuthorService = mongoAuthorService;
        this.mongoGenreService = mongoGenreService;
        this.mongoBookRepository = mongoBookRepository;
        this.batchRepository = batchRepository;
    }

    @Nullable
    public MongoBook findById(String id) {
        Optional<BatchItem> optionalBatch = batchRepository.findByImportLink(IMPORT_BOOK_JOB_NAME, id);
        MongoBook book = null;

        if (optionalBatch.isPresent()) {
            Optional<MongoBook> optionalMongoBook = mongoBookRepository.findById(optionalBatch.get().getExportLink());
            book = optionalMongoBook.orElse(null);
        }

        return book;
    }

    public MongoBook getMongoBook(Book book) {
        MongoAuthor mongoAuthor = mongoAuthorService.findById(String.valueOf(book.getAuthor().getId()));

        List<MongoGenre> mongoGenres = new ArrayList<>();
        book.getGenres().forEach(genre -> {
            MongoGenre mongoGenre = mongoGenreService.findById(String.valueOf(genre.getId()));
            if (mongoGenre != null) {
                mongoGenres.add(mongoGenre);
            }
        });

        return new MongoBook(
                book.getTitle(),
                mongoAuthor,
                mongoGenres
        );
    }
}
