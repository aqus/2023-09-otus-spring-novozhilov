package ru.otus.catalog.repositories;

import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.catalog.models.Book;
import ru.otus.catalog.models.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Jpa репозиторий комментариев к книгам")
@DataJpaTest
@Import({CommentRepositoryJpa.class})
public class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private List<Comment> dbComments;
    private List<Book> dbBooks;

    @BeforeEach
    void setUp() {
        dbComments = getDbComments();
        dbBooks = getDbBooks();
    }

    @DisplayName(" должен загружать комментарий по id")
    @Test
    void shouldLoadById() {
        Comment expectedComment = dbComments.get(0);
        Optional<Comment> actualComment = commentRepository.findById(expectedComment.getId());
        assertThat(actualComment).isPresent().get().isEqualTo(expectedComment);
    }

    @DisplayName(" должен загружать комментарии по id книги")
    @Test
    void shouldLoadCommentsByBookId() {
        List<Comment> actualComments = commentRepository.findAllByBookId(1);
        List<Comment> expectedComments = dbComments.stream().filter(c -> c.getBook().getId() == 1).toList();
        assertThat(actualComments).isNotEmpty();
        assertThat(actualComments).usingRecursiveComparison().isEqualTo(expectedComments);
    }

    @DisplayName(" должен сохранять новый коммент для книги")
    @Test
    void shouldSaveNewComment() {
        Optional<Book> book = dbBooks.stream().findFirst();
        List<Comment> bookComments = dbComments.stream()
                .filter(c -> c.getBook().getId() == book.get().getId())
                .toList();
        Comment comment = new Comment(null, "this is a new comment for the book", book.get());
        Comment savedComment = commentRepository.save(comment);

        assertThat(savedComment).isNotNull();

        List<Comment> actualComments = getDbComments().stream()
                .filter(c -> c.getBook().getId() == book.get().getId())
                .toList();

        assertThat(actualComments.size()).isEqualTo(bookComments.size() + 1);
    }

    @DisplayName(" должен удалять комментарий к книге")
    @Test
    void shouldDeleteComment() {
        Comment commentToDelete = dbComments.stream().findFirst().get();
        int sizeBeforeDeletion = dbComments.size();
        commentRepository.deleteById(commentToDelete.getId());
        int sizeAfterDeletion = getDbComments().size();
        assertThat(sizeAfterDeletion).isEqualTo(sizeBeforeDeletion - 1);
    }

    @DisplayName(" должен возвращать пустой результат для неверного id комментария")
    @Test
    void shouldReturnEmptyResultForWrongCommentId() {
        Optional<Comment> actualComment = commentRepository.findById(10);
        assertThat(actualComment).isNotPresent();
    }

    @DisplayName(" должен возвращать пустой результат для неверного id книги")
    @Test
    void shouldReturnEmptyResultForWrongBookId() {
        List<Comment> actualComments = commentRepository.findAllByBookId(10);
        assertThat(actualComments).isEmpty();
    }

    private List<Comment> getDbComments() {
        TypedQuery<Comment> query = testEntityManager.getEntityManager()
                .createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    private List<Book> getDbBooks() {
        TypedQuery<Book> query = testEntityManager.getEntityManager()
                .createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }
}
