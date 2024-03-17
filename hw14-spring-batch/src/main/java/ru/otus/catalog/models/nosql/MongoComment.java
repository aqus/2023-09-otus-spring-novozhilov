package ru.otus.catalog.models.nosql;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
public class MongoComment {
    @Id
    private String id;

    @Indexed(unique = true)
    private String text;

    @DBRef
    private MongoBook book;

    public MongoComment(String text, MongoBook book) {
        this.id = ObjectId.get().toString();
        this.text = text;
        this.book = book;
    }

    public MongoComment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MongoBook getBook() {
        return book;
    }

    public void setBook(MongoBook book) {
        this.book = book;
    }
}