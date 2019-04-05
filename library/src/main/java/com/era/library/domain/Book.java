package com.era.library.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Book {
    private final Long id;
    private final String caption;
    private final int year;
    private final Genre genre;
    private final Set<AuthorRef> authors = new HashSet<>();

    public void addAuthor(Author author){
        this.authors.add(new AuthorRef(author.getId()));
    }

    public Book(Long id, String caption, int year, Genre genre) {
        this.id = id;
        this.caption = caption;
        this.year = year;
        this.genre = genre;
    }

    public Long getId() {
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

    public Set<Long> getAuthors() {
        return this.authors.stream()
                .map(AuthorRef::getAuthor)
                .collect(Collectors.toSet());
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
