package org.uthmaniv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.*;

public class Library {


    Comparator<BorrowRequest> comparator = Comparator
            .comparingInt((BorrowRequest br) -> br.lender().getPriority())
            .thenComparingLong(BorrowRequest::timestamp);

    private static final Logger log = LogManager.getLogger(Library.class);
    private final BookShelf bookShelf;
    private final List<LendingHistory> lendingHistories = new ArrayList<>();
    private final Map<String, Lender> lenderMap;
    private final PriorityQueue<BorrowRequest> borrowRequests = new PriorityQueue<>(comparator);

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

    public Lender getLenderById(String lenderId) {
        if (lenderId == null || lenderId.isBlank()) {
            return null; // throw custom exception
        }
        return lenderMap.get(lenderId);
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
                            System.out.println("   -> " + book.title()));
                });
    }

    public void borrowBook(Lender lender, Book book) {
        if (bookShelf.isBookAvailable(book)) {
            bookShelf.removeFromShelf(book);
            lender.borrowBook(book);
            lendingHistories.add(new LendingHistory(lender, book, LocalDateTime.now()));
        } else {
            borrowRequests.offer(new BorrowRequest(lender, book, System.currentTimeMillis()));
            log.warn("No copies available for '{}'. Request queued for {}", book.title(), lender.getFirstName());
        }
    }

    public void returnBook(Lender lender, Book book) {
        lender.returnBook(book);
        bookShelf.addToShelf(book, 1);

        lendingHistories.stream()
                .filter(h -> h.getBookBorrowed().equals(book) && h.getReturnDate() == null)
                .findFirst()
                .ifPresent(h -> h.setReturnDate(LocalDateTime.now()));
    }


    public void printBorrowRequests() {
        if (borrowRequests.isEmpty()) {
            System.out.println("No pending borrow requests.");
            return;
        }

        System.out.printf("%-30s | %-40s | %-20s%n", "Lender Name", "Book Title", "Date Requested");
        System.out.println("=".repeat(95));

        // We don’t want to consume (poll) the queue, just display its elements
        borrowRequests.forEach(req -> {
            String lenderName = req.lender().getFirstName() + " " + req.lender().getLastName();
            String bookTitle = req.bookRequested().title();
            String dateRequested =
                    java.time.Instant.ofEpochMilli(req.timestamp())
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDateTime()
                            .toString();

            System.out.printf("%-30s | %-40s | %-20s%n", lenderName, bookTitle, dateRequested);
        });
    }

}
