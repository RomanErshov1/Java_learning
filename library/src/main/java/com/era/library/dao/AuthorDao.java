package com.era.library.dao;

import com.era.library.domain.Author;

import java.util.List;

public interface AuthorDao {
    int count();

    Author getById(int id);

    void insert(Author author);

    List<Author> getAll();

    void deleteById(int id);
}
