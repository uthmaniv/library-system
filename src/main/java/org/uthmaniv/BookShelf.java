package org.uthmaniv;

import java.util.List;
import java.util.Map;

public class BookShelf {
    private Map<Book,Integer> books;

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


    //methods
    public boolean isBookAvailable(Book book) {
        return books.getOrDefault(book, 0) > 0;
    }

    public void addToShelf(Book book, int quantity) {
        books.merge(book, quantity, Integer::sum);
    }

    public void removeFromShelf(Book book) {
        books.computeIfPresent(book, (b, count) -> (count > 1) ? count - 1 : null);
    }

    public Book getBookById(String id) {
        return books.keySet()
                .stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Book getBookByTitle(String title) {
        return books.keySet()
                .stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }



}
