package ru.otus.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateCommentDto {

    @NotBlank(message = "Mustn't be empty")
    private String text;

    @NotNull
    private String bookId;

    public CreateCommentDto(String text, String bookId) {
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

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
