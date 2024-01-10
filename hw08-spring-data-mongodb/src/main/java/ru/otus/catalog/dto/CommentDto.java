package ru.otus.catalog.dto;

import java.util.Objects;

public class CommentDto {

    private String id;

    private String text;

    private String bookId;

    public CommentDto(String id, String text, String bookId) {
        this.id = id;
        this.text = text;
        this.bookId = bookId;
    }

    public CommentDto() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentDto that = (CommentDto) o;
        return Objects.equals(id, that.id) && Objects.equals(text, that.text) && Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, bookId);
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", bookId='" + bookId + '\'' +
                '}';
    }
}
