package ru.otus.catalog.models.nosql;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "authors")
public class MongoAuthor {

    @Id
    private String id;

    @Indexed
    private String fullName;


    public MongoAuthor(String fullName) {
        this.id = ObjectId.get().toString();
        this.fullName = fullName;
    }

    public MongoAuthor() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}