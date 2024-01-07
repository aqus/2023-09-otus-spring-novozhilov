package ru.otus.catalog.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.catalog.dto.CommentDto;
import ru.otus.catalog.exceptions.EntityNotFoundException;
import ru.otus.catalog.mappers.CommentMapper;
import ru.otus.catalog.models.Book;
import ru.otus.catalog.models.Comment;
import ru.otus.catalog.repositories.BookRepository;
import ru.otus.catalog.repositories.CommentRepository;

import java.util.List;
import java.util.Objects;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public CommentDto findById(long id) {
        return CommentMapper.toCommentDto(commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id))));
    }

    @Override
    public List<CommentDto> findAllByBookId(long id) {
        Book book =
                bookRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found"
                                .formatted(id)
                        ));
        return commentRepository.findAllByBookId(book.getId())
                .stream()
                .map(CommentMapper::toCommentDto)
                .toList();
    }

    @Transactional
    @Override
    public CommentDto create(CommentDto commentDto) {
        Book book =
                bookRepository.findById(commentDto.getBookId())
                        .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found"
                                .formatted(commentDto.getBookId())
                        ));
        Comment newComment = new Comment(commentDto.getId(), commentDto.getText(), book);
        return CommentMapper.toCommentDto(
                commentRepository.save(newComment));
    }

    @Transactional
    @Override
    public CommentDto update(CommentDto commentDto) {
        Comment updatedComment =
                commentRepository.findById(commentDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found"
                                .formatted(commentDto.getId())));

        if (!Objects.equals(updatedComment.getBook().getId(), commentDto.getBookId())) {
            throw new IllegalStateException("Cannot change comment for another book");
        }
        updatedComment.setText(commentDto.getText());
        return CommentMapper.toCommentDto(commentRepository.save(updatedComment));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
