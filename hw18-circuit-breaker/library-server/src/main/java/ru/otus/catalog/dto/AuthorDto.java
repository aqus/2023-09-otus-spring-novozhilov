package ru.otus.catalog.dto;

import ru.otus.catalog.models.Author;

import java.util.Objects;

public class AuthorDto {

    private long id;

    private String fullName;

    public AuthorDto(long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public AuthorDto() {
    }

    public Author toModelObject() {
        return new Author(id, fullName);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthorDto authorDto = (AuthorDto) o;
        return id == authorDto.id && Objects.equals(fullName, authorDto.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName);
    }

    @Override
    public String toString() {
        return "AuthorDto{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
