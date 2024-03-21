package ru.otus.catalog.services.cleanup;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class BookCleanupService {

    private static final Logger LOG = Logger.getLogger(BookCleanupService.class.getName());

    public BookCleanupService() {
    }

    public void cleanUp() {
        LOG.info("Выполняю завершающие мероприятия...");
        LOG.info("Завершающие мероприятия закончены");
    }
}
