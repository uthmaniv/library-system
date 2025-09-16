package org.uthmaniv;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BorrowRequest (
        Lender lender,
        Book bookRequested,
        long timestamp
) {}

