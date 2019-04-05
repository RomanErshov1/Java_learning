package com.era.library.dao;

import com.era.library.domain.Genre;

import java.util.List;

public interface GenreDao {
    int count();

    Genre getById(int id);

    void insert(Genre genre);

    List<Genre> getAll();

    void deleteById(int id);
}
