package ru.otus.catalog.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.catalog.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public CommentRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAllByBookId(long id) {
        TypedQuery<Comment> query =
                em.createQuery("select c from Comment c where c.book.id = :id", Comment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public void deleteById(long id) {
        Comment comment = em.find(Comment.class, id);
        em.remove(comment);
    }
}
