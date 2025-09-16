package org.uthmaniv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class BookShelf {
    private Map<Book,Integer> books;
    private static final Logger log = LogManager.getLogger(BookShelf.class);

    public BookShelf(Map<Book,Integer> books) {
        this.books = books;
    }

    public List<Book> getAvailableBooks() {
        return books.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .toList();
    }

    public static void printAvailableBook(List<Book> books){
        System.out.printf("%-36s | %-40s | %-28s | %-15s | %-4s%n",
                "Book ID", "Title", "Author", "Genre", "Year");
        System.out.println("=".repeat(125));
        for (Book book: books){
            book.getBookDetails();
        }
    }

    public void printAvailableBooksWithCopies() {
        System.out.printf("%-36s | %-40s | %-28s | %-15s | %-4s | %-6s%n",
                "Book ID", "Title", "Author", "Genre", "Year", "Copies");
        System.out.println("=".repeat(140));

        books.forEach((book, copies) -> {
            if (copies > 0) {
                System.out.printf("%-36s | %-40s | %-28s | %-15s | %-4d | %-6d%n",
                        book.isbn(),
                        book.title(),
                        book.author(),
                        book.genre(),
                        book.publicationYear().getYear(),
                        copies
                );
            }
        });
    }


    public List<Book> getBooksByGenre(String genre) {
        return books.keySet()
                .stream()
                .filter(book -> book.genre().equalsIgnoreCase(genre))
                .toList();
    }

    //methods
    public boolean isBookAvailable(Book book) {
        return books.getOrDefault(book, 0) > 0;
    }

    public Book getBookById(long id) {
        return books.keySet()
                .stream()
                .filter(book -> book.isbn() == id)
                .findFirst()
                .orElse(null);
    }

    public Book getBookByTitle(String title) {
        return books.keySet()
                .stream()
                .filter(book -> book.title().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    public void addToShelf(Book book, int quantity) {
        books.merge(book, quantity, Integer::sum);
        log.debug("Added {} copies of '{}' to shelf. Total now: {}", quantity, book.title(), books.get(book));
    }

    public void removeFromShelf(Book book) {
        books.computeIfPresent(book, (b, count) -> (count > 1) ? count - 1 : null);
        log.debug("Removed 1 copy of '{}' from shelf.", book.title());
    }



}
