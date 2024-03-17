package ru.otus.catalog.models.nosql;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "genres")
public class MongoGenre {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    public MongoGenre(String name) {
        this.id = ObjectId.get().toString();
        this.name = name;
    }

    public MongoGenre() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
