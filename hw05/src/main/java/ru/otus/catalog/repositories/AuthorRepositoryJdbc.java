package ru.otus.catalog.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.otus.catalog.models.Author;

@Repository
public class AuthorRepositoryJdbc implements AuthorRepository {

    @Override
    public List<Author> findAll() {
        return null;
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.empty();
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            return null;
        }
    }
}
