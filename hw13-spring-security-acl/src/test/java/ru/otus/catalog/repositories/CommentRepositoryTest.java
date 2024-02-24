package ru.otus.catalog.repositories;

import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.catalog.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Jpa репозиторий комментариев к книгам")
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private List<Comment> dbComments;

    @BeforeEach
    void setUp() {
        dbComments = getDbComments();
    }

    @DisplayName(" должен загружать комментарии по id книги")
    @Test
    void shouldLoadCommentsByBookId() {
        List<Comment> actualComments = commentRepository.findAllByBookId(1);
        List<Comment> expectedComments = dbComments.stream().filter(c -> c.getBook().getId() == 1).toList();
        assertThat(actualComments).isNotEmpty();
        assertThat(actualComments).usingRecursiveComparison().isEqualTo(expectedComments);
    }

    private List<Comment> getDbComments() {
        TypedQuery<Comment> query = testEntityManager.getEntityManager()
                .createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }
}
