package ru.otus.catalog.services.cleanup;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class GenreCleanupService {

    private static final Logger LOG = Logger.getLogger(GenreCleanupService.class.getName());

    public GenreCleanupService() {
    }

    public void cleanUp() {
        LOG.info("Выполняю завершающие мероприятия...");
        LOG.info("Завершающие мероприятия закончены");
    }
}
