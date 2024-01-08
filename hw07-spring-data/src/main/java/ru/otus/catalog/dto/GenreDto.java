package ru.otus.catalog.dto;

import ru.otus.catalog.models.Genre;

import java.util.Objects;

public class GenreDto {

    private long id;

    private String name;

    public GenreDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreDto() {
    }

    public Genre toModelObject() {
        return new Genre(id, name);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GenreDto genreDto = (GenreDto) o;
        return id == genreDto.id && Objects.equals(name, genreDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "GenreDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
