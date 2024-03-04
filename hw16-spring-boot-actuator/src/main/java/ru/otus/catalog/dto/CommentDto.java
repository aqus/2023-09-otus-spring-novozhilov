package ru.otus.catalog.dto;

import java.util.Objects;

public class CommentDto {

    private Long id;

    private String text;

    private Long bookId;

    public CommentDto(Long id, String text, Long bookId) {
        this.id = id;
        this.text = text;
        this.bookId = bookId;
    }

    public CommentDto() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentDto that = (CommentDto) o;
        return Objects.equals(id, that.id) && Objects.equals(bookId, that.bookId) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, bookId);
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", bookId=" + bookId +
                '}';
    }
}
