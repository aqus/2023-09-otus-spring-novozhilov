package ru.otus.catalog.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfiguration {

    private final String jobCount;

    private final String author;

    private final String book;

    private final String comment;

    private final String genre;

    public ApplicationConfiguration(@Value("${app.job-count}") String jobCount,
                                    @Value("${app.current-item-count.author}") String author,
                                    @Value("${app.current-item-count.book}") String book,
                                    @Value("${app.current-item-count.comment}") String comment,
                                    @Value("${app.current-item-count.genre}") String genre) {
        this.jobCount = jobCount;
        this.author = author;
        this.book = book;
        this.comment = comment;
        this.genre = genre;
    }

    public String getJobCount() {
        return jobCount;
    }

    public String getAuthor() {
        return author;
    }

    public String getBook() {
        return book;
    }

    public String getComment() {
        return comment;
    }

    public String getGenre() {
        return genre;
    }
}
