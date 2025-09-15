package org.uthmaniv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class Lender {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final long phoneNumber;
    private final Set<Book> booksBorrowed;
    private static final Logger log = LogManager.getLogger(Lender.class);

    protected Lender(String id, String firstName, String lastName, long phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.booksBorrowed = new HashSet<>();
    }

    public abstract int getPriority();

    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public long getPhoneNumber() { return phoneNumber; }
    public Set<Book> getBooksBorrowed() { return booksBorrowed; }

    public void borrowBook(Book book) {
        if (booksBorrowed.contains(book)) {
            log.warn("{} {} attempted to borrow '{}' again but already has it.", firstName, lastName, book.getTitle());
        } else {
            booksBorrowed.add(book);
            log.info("{} {} borrowed '{}'", firstName, lastName, book.getTitle());
        }
    }

    public void returnBook(Book book) {
        if (booksBorrowed.remove(book)) {
            log.info("{} {} returned '{}'", firstName, lastName, book.getTitle());
        } else {
            log.warn("{} {} attempted to return '{}' but it was not borrowed.", firstName, lastName, book.getTitle());
        }
    }

    public void printBooksWithMe() {
        if (booksBorrowed.isEmpty()) {
            System.out.println(firstName + " " + lastName + " has no borrowed books.");
        } else {
            System.out.println("Books with " + firstName + " " + lastName + ":");
            System.out.printf("%-36s | %-40s | %-28s | %-15s | %-4s%n",
                    "Book ID", "Title", "Author", "Genre", "Year");
            System.out.println("=".repeat(125));
            booksBorrowed.forEach(Book::getBookDetails);
        }
    }

}
