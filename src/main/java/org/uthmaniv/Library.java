package org.uthmaniv;

import java.time.LocalDateTime;
import java.util.*;

public class Library {
    private final BookShelf bookShelf;
    private final List<LendingHistory> lendingHistories = new ArrayList<>();
    private PriorityQueue<BorrowRequest> borrowRequests;

    public Library(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }

    public BookShelf getBookShelf() {
        return bookShelf;
    }

    public List<LendingHistory> getLendingHistories() {
        return lendingHistories;
    }

    public PriorityQueue<BorrowRequest> getBorrowRequests() {
        return borrowRequests;
    }

    public void borrowBook(Lender lender, Book book) {
        if (bookShelf.isBookAvailable(book)) {
            bookShelf.removeFromShelf(book);
            lender.borrowBook(book);
            lendingHistories.add(new LendingHistory(lender, book, LocalDateTime.now()));
            System.out.println(lender.getFirstName() + " borrowed " + book.getTitle());
        } else {
            borrowRequests.offer(new BorrowRequest(lender, book, System.currentTimeMillis()));
            System.out.println("No copy available. Request queued for " + lender.getFirstName());
        }
    }

    public void returnBook(Lender lender, Book book) {
        lender.returnBook(book);
        bookShelf.addToShelf(book, 1);

        // mark history as returned
        lendingHistories.stream()
                .filter(h -> h.getBookBorrowed().equals(book) && h.getReturnDate() == null)
                .findFirst()
                .ifPresent(h -> h.setReturnDate(LocalDateTime.now()));

        // check if someone in queue is waiting
        if (!borrowRequests.isEmpty()) {
            BorrowRequest next = borrowRequests.poll();
            borrowBook(next.lender(), next.bookRequested());
        }
    }
}
