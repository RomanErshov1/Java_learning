package com.era.library.domain;

import java.util.List;

public class Author {
    private final int id;
    private final String name;
    private final String middleName;
    private final String surname;
    private final int birthdayYear;
    private final int deathYear;

    private final List<Book> books;

    public Author(int id, String name, String middleName, String surname, int birthdayYear, int deathYear,
                  List<Book> books) {
        this.id = id;
        this.name = name;
        this.middleName = middleName;
        this.surname = surname;
        this.birthdayYear = birthdayYear;
        this.deathYear = deathYear;
        this.books = books;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getSurname() {
        return surname;
    }

    public int getBirthdayYear() {
        return birthdayYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", surname='" + surname + '\'' +
                ", birthdayYear=" + birthdayYear +
                ", deathYear=" + deathYear +
                ", books=" + books +
                '}';
    }
}
