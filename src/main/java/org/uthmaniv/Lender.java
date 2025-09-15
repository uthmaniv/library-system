package org.uthmaniv;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class Lender {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final long phoneNumber;
    private final Set<Book> booksBorrowed;

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
        booksBorrowed.add(book);
        //check if we already borrowed, if we did , restrict us from borrowing thesame book
    }

    public void returnBook(Book book) {
        booksBorrowed.remove(book);
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
