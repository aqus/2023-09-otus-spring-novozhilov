package ru.otus.catalog.repositories.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.catalog.models.relational.BatchItem;

import java.util.List;
import java.util.Optional;

public interface BatchRepository extends JpaRepository<BatchItem, Long> {

    List<BatchItem> findAll();

    @Query("SELECT b FROM BatchItem b WHERE b.className = :className AND b.importLink = :importLink")
    Optional<BatchItem> findByImportLink(@Param("className") String className, @Param("importLink") String id);
}
