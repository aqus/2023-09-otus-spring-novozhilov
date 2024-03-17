package ru.otus.catalog.services.cleanup;

import org.springframework.stereotype.Service;
import ru.otus.catalog.models.relational.Author;
import ru.otus.catalog.repositories.relational.AuthorRepository;
import ru.otus.catalog.services.BatchService;

import java.util.List;
import java.util.logging.Logger;

import static ru.otus.catalog.configs.AuthorConfigJob.IMPORT_AUTHOR_JOB_NAME;

@Service
public class AuthorCleanUpService {

    private static final Logger LOG = Logger.getLogger(AuthorCleanUpService.class.getName());
    protected final AuthorRepository authorRepository;

    protected final BatchService batchService;

    public AuthorCleanUpService(AuthorRepository authorRepository, BatchService batchService) {
        this.authorRepository = authorRepository;
        this.batchService = batchService;
    }

    @SuppressWarnings("unused")
    public void cleanUp() {
        LOG.info("Выполняю завершающие мероприятия...");
        List<Long> exported = batchService.saveAllImported(IMPORT_AUTHOR_JOB_NAME);
        List<Author> authors = authorRepository.findAllById(exported);
        authors.forEach(author -> author.setImported(true));
        authorRepository.saveAll(authors);
        LOG.info("Завершающие мероприятия закончены");
    }
}
