package com.era.jpql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class JpqlApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JpqlApplication.class, args);
        BookRepository bookRepository = context.getBean(BookRepository.class);
        AuthorRepository authorRepository = context.getBean(AuthorRepository.class);

        Author bradbury = new Author("Ray Bradbury");
        Author azimov = new Author("Isaak Azimov");
        authorRepository.save(bradbury);
        authorRepository.save(azimov);

        bookRepository.save(new Book("Mars Chronicles", bradbury, LocalDate.of(1950, 6, 1)));
        bookRepository.save(new Book("451^F", bradbury, LocalDate.of(1957, 7, 1)));
        bookRepository.save(new Book("I Robot", azimov, LocalDate.of(1970, 1, 1)));
        Book book = bookRepository.findByName("Mars Chronicles").get(0);
        System.out.println("book " + book);

        List<String> bookNames = bookRepository.findNamesOfAuthorBooks("Ray Bradbury");
        System.out.println("bookNames = " + bookNames);
    }
}
