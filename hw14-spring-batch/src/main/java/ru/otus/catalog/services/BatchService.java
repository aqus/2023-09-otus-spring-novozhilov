package ru.otus.catalog.services;

import org.springframework.stereotype.Service;
import ru.otus.catalog.models.relational.BatchItem;
import ru.otus.catalog.repositories.relational.BatchRepository;

import java.util.List;

@Service
public class BatchService {

    private final BatchRepository batchRepository;

    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public void saveAll(List<BatchItem> batchItems) {
        batchRepository.saveAll(batchItems);
    }
}
