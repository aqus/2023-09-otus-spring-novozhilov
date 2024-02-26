package ru.otus.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateCommentDto {

    @NotNull
    private String id;

    @NotBlank(message = "Mustn't be empty")
    private String text;

    @NotNull
    private String bookId;

    public UpdateCommentDto(String id, String text, String bookId) {
        this.id = id;
        this.text = text;
        this.bookId = bookId;
    }

    public UpdateCommentDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
