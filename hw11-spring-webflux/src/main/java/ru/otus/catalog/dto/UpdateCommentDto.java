package ru.otus.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateCommentDto {

    @NotNull
    private Long id;

    @NotBlank(message = "Mustn't be empty")
    private String text;

    @NotNull
    private Long bookId;

    public UpdateCommentDto(Long id, String text, Long bookId) {
        this.id = id;
        this.text = text;
        this.bookId = bookId;
    }

    public UpdateCommentDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
