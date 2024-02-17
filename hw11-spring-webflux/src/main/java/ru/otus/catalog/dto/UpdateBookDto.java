package ru.otus.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class UpdateBookDto {

    @NotNull
    private String id;

    @NotBlank(message = "Mustn't be empty")
    private String title;

    @NotNull
    private String authorId;

    @NotNull
    private List<String> genresIds;

    public UpdateBookDto(String id, String title, String authorId, List<String> genresIds) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.genresIds = genresIds;
    }

    public UpdateBookDto() {
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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public List<String> getGenresIds() {
        return genresIds;
    }

    public void setGenresIds(List<String> genresIds) {
        this.genresIds = genresIds;
    }
}
