package org.uthmaniv;

import java.time.LocalDateTime;
import java.util.List;

public class LendingHistory {
    private final Lender lender;
    private final Book bookBorrowed;
    private final LocalDateTime borrowDate;
    private LocalDateTime returnDate;

    public LendingHistory(Lender lender, Book bookBorrowed, LocalDateTime borrowDate) {
        this.lender = lender;
        this.bookBorrowed = bookBorrowed;
        this.borrowDate = borrowDate;
    }

    public Lender getLender() {
        return lender;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public Book getBookBorrowed() {
        return bookBorrowed;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
        //log
    }

    public void printLendingHistories(List<LendingHistory> lendingHistories) {
        System.out.printf("%-36s %-20s %-30s %-20s %-20s%n",
                "Lender ID", "Lender Name", "Book Title", "Borrowed At", "Returned At");
        System.out.println("=".repeat(130));

        for (LendingHistory history : lendingHistories) {
            String lenderName = history.getLender().getFirstName() + " " + history.getLender().getLastName();
            String borrowDate = history.getBorrowDate().toString();
            String returnDate = (history.getReturnDate() != null) ? history.getReturnDate().toString() : "Not Returned";

            System.out.printf("%-36s %-20s %-30s %-20s %-20s%n",
                    history.getLender().getId(),
                    lenderName,
                    history.getBookBorrowed().getTitle(),
                    borrowDate,
                    returnDate
            );
        }
    }

}
