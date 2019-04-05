package com.era.library.dao;

import com.era.library.domain.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private static class GenreMapper implements RowMapper<Genre>{
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("genre_name");
            return new Genre(id, name);
        }
    }

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("select count(*) from tbl_genre",
                new HashMap<>(), Integer.class);
    }

    @Override
    public Genre getById(int id) {
       final HashMap<String, Object> params = new HashMap<>(1);
       params.put("id", id);
       return namedParameterJdbcOperations.queryForObject("select * from tbl_genre where id = :id",
               params,
               new GenreMapper());
    }

    @Override
    public void insert(Genre genre) {
        final HashMap<String, Object> params = new HashMap<>(2);
        params.put("id", genre.getId());
        params.put("genre_name", genre.getGenreName());
        namedParameterJdbcOperations.update("insert into tbl_genre (id, `genre_name`) values (:id, :genre_name)",
                params);
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query("select * from tbl_genre", new GenreMapper());
    }

    @Override
    public void deleteById(int id) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        namedParameterJdbcOperations.update("delete from tbl_genre where id = :id", params);
    }
}
