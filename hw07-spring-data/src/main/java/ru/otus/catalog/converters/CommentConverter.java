package ru.otus.catalog.converters;

import org.springframework.stereotype.Component;
import ru.otus.catalog.dto.CommentDto;

@Component
public class CommentConverter {

    public String commentToString(CommentDto commentDto) {
        return "Id: %d, text: %s, bookId: {%d}".formatted(
                commentDto.getId(),
                commentDto.getText(),
                commentDto.getBookId());
    }
}
