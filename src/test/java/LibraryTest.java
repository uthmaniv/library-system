
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uthmaniv.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library library;
    private BookShelf bookShelf;
    private Student student;

    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        Map<Book, Integer> books = new HashMap<>();
        book1 = new Book(4444,"Clean Code", "Robert Martin", "Programming", LocalDate.of(2008, 8, 1));
        book2 = new Book(3333,"Animal Farm", "George Orwell", "Dystopian", LocalDate.of(1945, 8, 17));

        books.put(book1, 2);
        books.put(book2, 1);

        bookShelf = new BookShelf(books);

        Map<String, Lender> lenderMap = new HashMap<>();
        student = new Student("STU001", "Usman", "Yahaya", 12345L, ClassLevel.SSS2);
        lenderMap.put(student.getId(), student);

        library = new Library(bookShelf, lenderMap);
    }

    @Test
    void testBorrowBookDecreasesCopies() {
        int before = bookShelf.getAvailableBooks().size();
        library.borrowBook(student, book1);

        assertTrue(student.getBooksBorrowed().contains(book1));
        assertEquals(1, library.getBookShelf().getBooksByGenre("Programming").size());
        assertEquals(1, bookShelf.getBooksByGenre("Programming").size());
    }

    @Test
    void testBorrowSameBookTwiceNotAllowed() {
        library.borrowBook(student, book1);
        library.borrowBook(student, book1);

        assertEquals(1, student.getBooksBorrowed().size(), "Should not allow duplicate borrow");
    }

    @Test
    void testReturnBookIncreasesCopies() {
        library.borrowBook(student, book1);
        library.returnBook(student, book1);

        assertFalse(student.getBooksBorrowed().contains(book1));
        assertTrue(bookShelf.isBookAvailable(book1));
    }

    @Test
    void testGetBookByTitle() {
        Book found = bookShelf.getBookByTitle("Clean Code");
        assertNotNull(found);
        assertEquals("Clean Code", found.title());
    }

    @Test
    void testGetBooksByGenre() {
        var dystopianBooks = bookShelf.getBooksByGenre("Dystopian");
        assertEquals(1, dystopianBooks.size());
        assertEquals("Animal Farm", dystopianBooks.get(0).title());
    }

    @Test
    void testGetLenderById() {
        Lender found = library.getLenderById("STU001");
        assertNotNull(found);
        assertEquals("Usman", found.getFirstName());
    }

    @Test
    void testLendingHistoryIsRecorded() {
        library.borrowBook(student, book1);

        assertEquals(1, library.getLendingHistories().size());
        assertEquals(book1, library.getLendingHistories().get(0).getBookBorrowed());
    }
}
