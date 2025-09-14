package org.uthmaniv;

import java.time.LocalDateTime;
import java.util.*;

public class Library {
    private final BookShelf bookShelf;
    private final List<LendingHistory> lendingHistories = new ArrayList<>();
    private final PriorityQueue<BorrowRequest> borrowRequests;

    public Library(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        this.borrowRequests = new PriorityQueue<>(Comparator
                .comparingInt((BorrowRequest r) -> r.lender().getPriority())
                .thenComparingLong(BorrowRequest::timestamp));
    }

    public void borrowBook(Lender lender, String title) {
        Book book = bookShelf.getBookByTitle(title);
        if (book == null) {
            System.out.println("Book not found: " + title);
            return;
        }

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

    public void returnBook(Lender lender, String title) {
        Book book = bookShelf.getBookByTitle(title);
        if (book == null) return;

        lender.returnBook(book);
        bookShelf.addToShelf(book, 1);

        // mark history as returned
        lendingHistories.stream()
                .filter(h -> h.bookBorrowed().equals(book) && h.returnDate == null)
                .findFirst()
                .ifPresent(h -> h.setReturnDate(LocalDateTime.now()));

        // check if someone in queue is waiting
        if (!borrowRequests.isEmpty()) {
            BorrowRequest next = borrowRequests.poll();
            borrowBook(next.lender(), next.bookRequested().getTitle());
        }
    }
}
