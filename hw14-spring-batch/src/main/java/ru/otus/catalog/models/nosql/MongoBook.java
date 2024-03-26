package ru.otus.catalog.models.nosql;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "books")
public class MongoBook {
    @Id
    private String id;

    @Indexed(unique = true)
    private String title;

    private MongoAuthor author;

    @DBRef
    private List<MongoGenre> genres;

    public MongoBook(String title, MongoAuthor author, List<MongoGenre> genres) {
        this.id = ObjectId.get().toString();
        this.title = title;
        this.author = author;
        this.genres = genres;
    }

    public MongoBook() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MongoAuthor getAuthor() {
        return author;
    }

    public void setAuthor(MongoAuthor author) {
        this.author = author;
    }

    public List<MongoGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<MongoGenre> genres) {
        this.genres = genres;
    }
}