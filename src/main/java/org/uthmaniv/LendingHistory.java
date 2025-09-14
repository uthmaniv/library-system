package org.uthmaniv;

import java.time.LocalDateTime;

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
}
