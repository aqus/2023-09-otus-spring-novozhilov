package ru.otus.catalog.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;

@ChangeLog
public class MongoDBChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "ivan.novozhilov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
        db.createCollection("authors");
        db.createCollection("genres");
        db.createCollection("books");
        db.createCollection("comments");
    }
}
