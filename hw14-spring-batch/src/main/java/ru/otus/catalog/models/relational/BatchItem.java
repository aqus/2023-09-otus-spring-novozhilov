package ru.otus.catalog.models.relational;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "batch_items")
public class BatchItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String className;

    private String importLink;

    private String exportLink;

    public BatchItem(String className, String importLink, String exportLink) {
        this.className = className;
        this.importLink = importLink;
        this.exportLink = exportLink;
    }

    public BatchItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getImportLink() {
        return importLink;
    }

    public void setImportLink(String importLink) {
        this.importLink = importLink;
    }

    public String getExportLink() {
        return exportLink;
    }

    public void setExportLink(String exportLink) {
        this.exportLink = exportLink;
    }

}
