package com.era.library.domain;

import java.util.List;

public class Book {
    private final int id;
    private final String caption;
    private final int year;
    private final Genre genre;
    private final List<Author> authors;

    public Book(int id, String caption, int year, Genre genre, List<Author> authors) {
        this.id = id;
        this.caption = caption;
        this.year = year;
        this.genre = genre;
        this.authors = authors;
    }

    public int getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public int getYear() {
        return year;
    }

    public Genre getGenre() {
        return genre;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", year=" + year +
                ", genre=" + genre +
                ", authors=" + authors +
                '}';
    }
}
