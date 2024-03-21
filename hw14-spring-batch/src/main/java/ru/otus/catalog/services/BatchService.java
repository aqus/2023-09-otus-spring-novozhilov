package ru.otus.catalog.services;

import org.springframework.stereotype.Service;
import ru.otus.catalog.models.relational.BatchItem;
import ru.otus.catalog.repositories.relational.BatchRepository;

@Service
public class BatchService {

    private final BatchRepository batchRepository;

    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public void insert(String className, String importId, String exportId) {
        batchRepository.save(new BatchItem(className, importId, exportId));
    }
}
