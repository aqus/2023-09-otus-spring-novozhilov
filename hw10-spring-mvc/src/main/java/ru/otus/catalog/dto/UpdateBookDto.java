package ru.otus.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class UpdateBookDto {

    @NotNull
    private Long id;

    @NotBlank(message = "Mustn't be empty")
    private String title;

    private @NotNull Long authorId;

    @NotNull
    private List<Long> genresIds;

    public UpdateBookDto(Long id, String title, Long authorId, List<Long> genresIds) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.genresIds = genresIds;
    }

    public UpdateBookDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public List<Long> getGenresIds() {
        return genresIds;
    }

    public void setGenresIds(List<Long> genresIds) {
        this.genresIds = genresIds;
    }
}
