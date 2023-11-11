package ru.otus.catalog.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.otus.catalog.models.Genre;

@Repository
public class GenreRepositoryJdbc implements GenreRepository {
    @Override
    public List<Genre> findAll() {
        return new ArrayList<>();
    }

    @Override
    public List<Genre> findAllByIds(List<Long> ids) {
        return new ArrayList<>();
    }

    private static class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            return null;
        }
    }
}
