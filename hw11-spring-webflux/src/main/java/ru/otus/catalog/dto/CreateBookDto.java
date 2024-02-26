package ru.otus.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateBookDto {

    @NotBlank(message = "Mustn't be empty")
    private String title;

    private @NotNull String authorId;

    @NotNull
    private List<String> genresIds;

    public CreateBookDto(String title, String authorId, List<String> genresIds) {
        this.title = title;
        this.authorId = authorId;
        this.genresIds = genresIds;
    }

    public CreateBookDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public @NotNull String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(@NotNull String authorId) {
        this.authorId = authorId;
    }

    public List<String> getGenresIds() {
        return genresIds;
    }

    public void setGenresIds(List<String> genresIds) {
        this.genresIds = genresIds;
    }
}
