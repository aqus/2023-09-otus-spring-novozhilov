package ru.otus.catalog.services.cleanup;

import org.springframework.stereotype.Service;
import ru.otus.catalog.models.relational.Book;
import ru.otus.catalog.repositories.relational.BookRepository;
import ru.otus.catalog.services.BatchService;

import java.util.List;
import java.util.logging.Logger;

import static ru.otus.catalog.configs.BookConfigJob.IMPORT_BOOK_JOB_NAME;

@Service
public class BookCleanupService {

    private static final Logger LOG = Logger.getLogger(BookCleanupService.class.getName());

    private final BookRepository bookRepository;

    private final BatchService batchService;

    public BookCleanupService(BookRepository bookRepository, BatchService batchService) {
        this.bookRepository = bookRepository;
        this.batchService = batchService;
    }

    public void cleanUp() {
        LOG.info("Выполняю завершающие мероприятия...");
        List<Long> exported = batchService.saveAllImported(IMPORT_BOOK_JOB_NAME);
        List<Book> books = bookRepository.findAllById(exported);
        books.forEach(book -> book.setImported(true));
        bookRepository.saveAll(books);
        LOG.info("Завершающие мероприятия закончены");
    }
}
