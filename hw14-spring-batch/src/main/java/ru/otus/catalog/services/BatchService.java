package ru.otus.catalog.services;

import org.springframework.stereotype.Service;
import ru.otus.catalog.models.Batch;
import ru.otus.catalog.repositories.BatchRepository;

import java.util.List;

@Service
public class BatchService {

    private final BatchRepository batchRepository;

    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public void insert(String className, String importId, String exportId) {
        batchRepository.save(new Batch(className, importId, exportId, false));
    }

    public List<Batch> findByClassName(String className) {
        return batchRepository.findNotImportedByClassName(className);
    }

    public List<Long> saveAllImported(String importJobName) {
        List<Batch> batchLinks = batchRepository.findNotImportedByClassName(importJobName);
        batchLinks.forEach(batchLink -> batchLink.setImported(true));
        return batchRepository.saveAll(batchLinks).stream()
                .map(batchLink -> Long.valueOf(batchLink.getImportLink()))
                .toList();
    }
}
