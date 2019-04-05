package com.era.library.dao;

import com.era.library.domain.Author;
import com.era.library.domain.Book;
import com.era.library.domain.Genre;

import java.util.List;

public interface BookDao {

    int count();

    void insert (Book book);

    Book getById(int id);

    List<Book> getAll();

    void deleteById(int id);

    List<Book> findByAuthor(Author author);

    List<Book> findByGenre(Genre genre);
}
