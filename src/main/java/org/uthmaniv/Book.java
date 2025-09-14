package org.uthmaniv;

import java.time.LocalDate;
import java.util.UUID;

public class Book {
    private final String id;
    private final String title;
    private final String author;
    private final String genre;
    private final LocalDate publicationYear;

    public Book(String title, String author, String genre, LocalDate publicationYear) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public LocalDate getPublicationYear() { return publicationYear; }

    @Override
    public String toString() {
        return "Book{" + "title='" + title + "', author='" + author + "'}";
    }
}
