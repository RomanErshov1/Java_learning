package com.era.library.dao;

import com.era.library.domain.Author;
import com.era.library.domain.Book;
import com.era.library.domain.Genre;

import java.util.List;

public class BookDaoJdbc implements BookDao {
    @Override
    public int count() {
        return 0;
    }

    @Override
    public void insert(Book book) {

    }

    @Override
    public Book getById(int id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public List<Book> findByAuthor(Author author) {
        return null;
    }

    @Override
    public List<Book> findByGenre(Genre genre) {
        return null;
    }
}
