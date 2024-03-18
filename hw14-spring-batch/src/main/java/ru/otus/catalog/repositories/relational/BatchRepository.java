package ru.otus.catalog.repositories.relational;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.catalog.models.relational.Batch;

import java.util.List;
import java.util.Optional;

public interface BatchRepository extends JpaRepository<Batch, Long> {

    List<Batch> findAll();

    @Query("SELECT b FROM Batch b WHERE b.className = :className AND b.imported = false")
    List<Batch> findNotImportedByClassName(String className);

    @Query("SELECT b FROM Batch b WHERE b.className = :className AND b.imported = true")
    List<Batch> findImportedByClassName(String className);

    @Query("SELECT b FROM Batch b WHERE b.className = :className AND b.importLink = :importLink")
    Optional<Batch> findByImportLink(@Param("className") String className, @Param("importLink") String id);
}
