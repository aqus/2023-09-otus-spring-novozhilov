package ru.otus.catalog.services.cleanup;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AuthorCleanupService {

    private static final Logger LOG = Logger.getLogger(AuthorCleanupService.class.getName());

    public AuthorCleanupService() {
    }

    public void cleanUp() {
        LOG.info("Выполняю завершающие мероприятия...");
        LOG.info("Завершающие мероприятия закончены");
    }
}
