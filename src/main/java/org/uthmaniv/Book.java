package org.uthmaniv;

import java.time.LocalDate;
import java.util.Objects;

public record Book(long isbn, String title, String author, String genre, LocalDate publicationYear) {

    @Override
    public String toString() {
        return "Book{" + "title='" + title + "', author='" + author + "'}";
    }

    public void getBookDetails() {
        System.out.printf("%-36s | %-40s | %-28s | %-15s | %-4d%n",
                isbn,
                title,
                author,
                genre,
                publicationYear.getYear()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn == book.isbn;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }
}
