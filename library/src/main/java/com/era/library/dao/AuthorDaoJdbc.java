package com.era.library.dao;

import com.era.library.domain.Author;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private static class AuthorMapper implements RowMapper<Author>{
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("author_name");
            String middlename = resultSet.getString("author_middlename");
            String surname = resultSet.getString("author_surname");
            int birthday = resultSet.getInt("birthday_year");
            int death = resultSet.getInt("death_year");
            return new Author(id, name, middlename, surname, birthday, death, new ArrayList<>());
        }
    }

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("select count(*) from tbl_author",
                new HashMap<>(), Integer.class);
    }

    @Override
    public Author getById(int id) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return namedParameterJdbcOperations.queryForObject("select tbl_author.id, tbl_author.author_name," +
                        "tbl_author.author_middlename, tbl_author.author_surname, tbl_author.author_birthday_year, " +
                        "tbl_author.author_death_year, tbl_book.id, tbl_book.caption, tbl_book.year, " +
                        "tbl_genre.id, tbl_genre.genre_name from tbl_author " +
                        "left outer join tbl_books_authors on tbl_author.id = tbl_books_authors.author_id " +
                        "left outer join tbl_book on tbl_books_authors.book_id = tbl_book.id " +
                        "inner join tbl_genre on tbl_book.genre_id = tbl_genre.id " +
                        "where tbl_author.id = :id",
                params, new AuthorMapper());
    }

    @Override
    public void insert(Author author) {

    }

    @Override
    public List<Author> getAll() {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
