package ru.otus.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateBookDto {

    @NotBlank(message = "Mustn't be empty")
    private String title;

    private @NotNull Long authorId;

    @NotNull
    private List<Long> genresIds;

    public CreateBookDto(String title, Long authorId, List<Long> genresIds) {
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

    public @NotNull Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(@NotNull Long authorId) {
        this.authorId = authorId;
    }

    public List<Long> getGenresIds() {
        return genresIds;
    }

    public void setGenresIds(List<Long> genresIds) {
        this.genresIds = genresIds;
    }
}
