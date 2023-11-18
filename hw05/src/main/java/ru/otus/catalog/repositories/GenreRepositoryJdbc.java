package ru.otus.catalog.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import ru.otus.catalog.models.Genre;

@Repository
public class GenreRepositoryJdbc implements GenreRepository {

    private final NamedParameterJdbcOperations namedParameterJdbc;

    public GenreRepositoryJdbc(NamedParameterJdbcOperations namedParameterJdbc) {
        this.namedParameterJdbc = namedParameterJdbc;
    }

    @Override
    public List<Genre> findAll() {
        return namedParameterJdbc.query(
                "SELECT id, name FROM genres",
                new GenreRowMapper());
    }

    @Override
    public List<Genre> findAllByIds(List<Long> ids) {
        return namedParameterJdbc.query(
                "SELECT id, name FROM genres where id IN (:ids)",
                Map.of("ids", ids),
                new GenreRowMapper());
    }

    private static class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }
}
