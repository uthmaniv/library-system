package org.uthmaniv;

import java.time.LocalDateTime;
import java.util.*;

public class Library {
    private final BookShelf bookShelf;
    private final List<LendingHistory> lendingHistories = new ArrayList<>();
    private final Map<String, Lender> lenderMap;
    private PriorityQueue<BorrowRequest> borrowRequests;

    public Library(BookShelf bookShelf, Map<String, Lender> lenderMap) {
        this.bookShelf = bookShelf;
        this.lenderMap = lenderMap;
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

    public Map<String, Lender> getLenderMap() {
        return lenderMap;
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

    public Lender getLenderById(String lenderId) {
        if (lenderId == null || lenderId.isBlank()) {
            return null; // throw custom exception
        }
        return lenderMap.get(lenderId);
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
//        if (!borrowRequests.isEmpty()) {
//            BorrowRequest next = borrowRequests.poll();
//            borrowBook(next.lender(), next.bookRequested());
//        }
    }

    public void printLendingHistories() {
        List<LendingHistory> histories = this.getLendingHistories();
        if (histories.isEmpty()) {
            System.out.println("No lending history yet.");
            return;
        }

        System.out.println("\n=== Lending Histories ===");
        // Print header
        System.out.printf("| %-40s | %-40s | %-20s |%n", "Name", "Borrow Date", "Return Date");
        System.out.println("------------------------------------------------------------------------------------------------------");

        // Print each history record
        for (LendingHistory h : histories) {
            String name = h.getLender().getFirstName() + " " + h.getLender().getLastName();
            String borrowDate = h.getBorrowDate().toString();
            String returnDate = (h.getReturnDate() != null) ? h.getReturnDate().toString() : "Not yet returned";

            System.out.printf("| %-40s | %-40s | %-20s |%n", name, borrowDate, returnDate);
        }
    }


    public void printCurrentLenders() {
        System.out.println("\n=== Current Lenders with Books ===");
        this.getLenderMap().values().stream()
                .filter(l -> !l.getBooksBorrowed().isEmpty())
                .forEach(l -> {
                    System.out.println(l.getFirstName() + " " + l.getLastName() + " | ID: " + l.getId());
                    l.getBooksBorrowed().forEach(book ->
                            System.out.println("   -> " + book.getTitle()));
                });
    }


}
