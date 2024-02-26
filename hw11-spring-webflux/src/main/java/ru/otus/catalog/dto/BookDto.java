package ru.otus.catalog.dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.otus.catalog.models.Book;

public class BookDto {

    private String id;

    private String title;

    private AuthorDto authorDto;

    private List<GenreDto> genreDtos;

    public BookDto(String id, String title, AuthorDto authorDto, List<GenreDto> genreDtos) {
        this.id = id;
        this.title = title;
        this.authorDto = authorDto;
        this.genreDtos = genreDtos;
    }

    public BookDto() {
    }

    public Book toModelObject() {
        return new Book(id, title, authorDto.toModelObject(),
                genreDtos.stream().map(GenreDto::toModelObject).collect(Collectors.toList()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AuthorDto getAuthorDto() {
        return authorDto;
    }

    public void setAuthorDto(AuthorDto authorDto) {
        this.authorDto = authorDto;
    }

    public List<GenreDto> getGenreDtos() {
        return genreDtos;
    }

    public void setGenreDtos(List<GenreDto> genreDtos) {
        this.genreDtos = genreDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookDto bookDto = (BookDto) o;
        return Objects.equals(id, bookDto.id) && Objects.equals(title, bookDto.title) && Objects.equals(authorDto,
                bookDto.authorDto)
                && Objects.equals(genreDtos, bookDto.genreDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, authorDto, genreDtos);
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorDto=" + authorDto +
                ", genreDtos=" + genreDtos +
                '}';
    }
}
