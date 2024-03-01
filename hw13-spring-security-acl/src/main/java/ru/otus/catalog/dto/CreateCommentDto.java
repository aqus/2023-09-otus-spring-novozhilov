package ru.otus.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateCommentDto {

    @NotBlank(message = "Mustn't be empty")
    private String text;

    @NotNull
    private Long bookId;

    public CreateCommentDto(String text, Long bookId) {
        this.text = text;
        this.bookId = bookId;
    }

    public CreateCommentDto() {
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
