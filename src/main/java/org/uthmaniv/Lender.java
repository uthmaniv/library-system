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

    protected Lender(String firstName, String lastName, long phoneNumber) {
        this.id = UUID.randomUUID().toString();
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
    }

    public void returnBook(Book book) {
        booksBorrowed.remove(book);
    }
}
