package ru.otus.catalog.services.cleanup;

import org.springframework.stereotype.Service;
import ru.otus.catalog.models.relational.Genre;
import ru.otus.catalog.repositories.relational.GenreRepository;
import ru.otus.catalog.services.BatchService;

import java.util.List;
import java.util.logging.Logger;

import static ru.otus.catalog.configs.GenreConfigJob.IMPORT_GENRE_JOB_NAME;

@Service
public class GenreCleanupService {

    private static final Logger LOG = Logger.getLogger(GenreCleanupService.class.getName());

    private final GenreRepository genreRepository;

    private final BatchService batchService;


    public GenreCleanupService(GenreRepository genreRepository, BatchService batchService) {
        this.genreRepository = genreRepository;
        this.batchService = batchService;
    }

    public void cleanUp() {
        LOG.info("Выполняю завершающие мероприятия...");
        List<Long> exported = batchService.saveAllImported(IMPORT_GENRE_JOB_NAME);
        List<Genre> genres = genreRepository.findAllById(exported);
        genres.forEach(genre -> genre.setImported(true));
        genreRepository.saveAll(genres);
        LOG.info("Завершающие мероприятия закончены");
    }
}
