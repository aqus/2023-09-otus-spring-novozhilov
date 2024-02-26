package ru.otus.catalog.converters;

import org.springframework.stereotype.Component;
import ru.otus.catalog.dto.CommentDto;

@Component
public class CommentConverter {

    public String commentToString(CommentDto commentDto) {
        return "Id: %s, text: %s, bookId: {%s}".formatted(
                commentDto.getId(),
                commentDto.getText(),
                commentDto.getBookId());
    }
}
