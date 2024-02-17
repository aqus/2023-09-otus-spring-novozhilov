package ru.otus.catalog.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import reactor.core.publisher.Mono;
import ru.otus.catalog.models.Author;
import ru.otus.catalog.models.Book;
import ru.otus.catalog.models.Comment;
import ru.otus.catalog.models.Genre;
import ru.otus.catalog.repositories.AuthorRepository;
import ru.otus.catalog.repositories.BookRepository;
import ru.otus.catalog.repositories.CommentRepository;
import ru.otus.catalog.repositories.GenreRepository;

import java.util.List;

@ChangeLog
public class MongoDBChangelog {

    private List<Mono<Author>> authors;

    private List<Mono<Genre>> genres;

    private List<Mono<Book>> books;

    @ChangeSet(order = "001", id = "dropDb", author = "ivan.novozhilov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "ivan.novozhilov", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        var author1 = repository.save(new Author("Author_1"));
        var author2 = repository.save(new Author("Author_2"));
        var author3 = repository.save(new Author("Author_3"));
        authors = List.of(author1, author2, author3);
    }

    @ChangeSet(order = "003", id = "initGenres", author = "ivan.novozhilov", runAlways = true)
    public void initGenres(GenreRepository repository) {
        var genre1 = repository.save(new Genre("Genre_1"));
        var genre2 = repository.save(new Genre("Genre_2"));
        var genre3 = repository.save(new Genre("Genre_3"));
        var genre4 = repository.save(new Genre("Genre_4"));
        var genre5 = repository.save(new Genre("Genre_5"));
        var genre6 = repository.save(new Genre("Genre_6"));
        genres = List.of(genre1, genre2, genre3, genre4, genre5, genre6);
    }

    @ChangeSet(order = "004", id = "initBooks", author = "ivan.novozhilov", runAlways = true)
    public void initBooks(BookRepository repository) {
        var book1 = repository.save(new Book("BookTitle_1", authors.get(0).block(),
                List.of(genres.get(0).block(), genres.get(1).block())));
        var book2 = repository.save(new Book("BookTitle_2", authors.get(1).block(),
                List.of(genres.get(2).block(), genres.get(3).block())));
        var book3 = repository.save(new Book("BookTitle_3", authors.get(2).block(),
                List.of(genres.get(4).block(), genres.get(5).block())));
        books = List.of(book1, book2, book3);
    }

    @ChangeSet(order = "005", id = "initComments", author = "ivan.novozhilov", runAlways = true)
    public void initComments(CommentRepository repository) {
        repository.save(new Comment("BookComment_1", books.get(0).block())).block();
        repository.save(new Comment("BookComment_2", books.get(1).block())).block();
        repository.save(new Comment("BookComment_3", books.get(2).block())).block();
    }
}
