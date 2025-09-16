package org.uthmaniv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.*;

import static org.uthmaniv.BookShelf.printAvailableBook;

public class LibrarySystem {
    private static final Logger log = LogManager.getLogger(LibrarySystem.class);

    public static void main(String[] args) {
        Map<Book, Integer> initialBooks = new HashMap<>();
        Map<String, Lender> lenderMap = new HashMap<>();
        Set<ClassLevel> seniorClass = new HashSet<>(List.of(
                ClassLevel.SSS1,
                ClassLevel.SSS2,
                ClassLevel.SSS3)
        );
        Set<ClassLevel> juniorClass = new HashSet<>(List.of(
                ClassLevel.JSS1,
                ClassLevel.JSS2,
                ClassLevel.JSS3)
        );

        Book b1 = new Book(1111,"The Alchemist", "Paulo Coelho", "Self-Help", LocalDate.of(1990, 10, 23));
        Book b2 = new Book(2222,"Deep Work", "Carl Newport", "Self-Help", LocalDate.of(2018, 3, 16));
        Book b3 = new Book(3333,"Animal Farm", "George Orwell", "Dystopian", LocalDate.of(1949, 6, 8));
        Book b4 = new Book(4444,"Clean Code", "Robert C. Martin", "Programming", LocalDate.of(2008, 8, 1));
        Book b5 = new Book(5555,"Atomic Habits", "James Clear", "Self-Help", LocalDate.of(2018, 10, 16));
        Book b6 = new Book(6666,"The Pragmatic Programmer", "Andrew Hunt & David Thomas", "Programming", LocalDate.of(1999, 10, 30));
        Book b7 = new Book(7777,"Thinking, Fast and Slow", "Daniel Kahneman", "Psychology", LocalDate.of(2011, 10, 25));
        Book b8 = new Book(8888,"Sapiens: A Brief History of Humankind", "Yuval Noah Harari", "History", LocalDate.of(2011, 9, 4));
        Book b9 = new Book(9999,"The 7 Habits of Highly Effective People", "Stephen R. Covey", "Self-Help", LocalDate.of(1989, 8, 15));
        Book b10 = new Book(1010,"Introduction to Algorithms", "Thomas H. Cormen", "Programming", LocalDate.of(1990, 9, 1));

        initialBooks.put(b1, 2);
        initialBooks.put(b2, 2);
        initialBooks.put(b3, 2);
        initialBooks.put(b4, 2);
        initialBooks.put(b5, 2);
        initialBooks.put(b6, 2);
        initialBooks.put(b7, 2);
        initialBooks.put(b8, 2);
        initialBooks.put(b9, 2);
        initialBooks.put(b10,2);

        // Sample lenders
        Lender student1 = new Student("CS1080","Usman", "Yahaya Baba", 903535353, ClassLevel.SSS2);
        Lender student2 = new Student("CS1070","Mahadi", "Abuhuraira", 423232333, ClassLevel.SSS2);
        Lender student3 = new Student("CS1033","Anas", "Yakubu", 3626323, ClassLevel.SSS1);
        Lender student4 = new Student("CS1043","Mustapha", "Sanusi", 2323232, ClassLevel.JSS3);
        Lender student5 = new Student("CS1041","Ibrahim", "Sheme", 232323, ClassLevel.JSS1);
        Lender teacher1 = new Teacher("CST01","Abdulrazaq", "Musa", 883278273, seniorClass);
        Lender teacher2 = new Teacher("CST02","Tanko", "Abubakar", 323222, juniorClass);

        lenderMap.put(student1.getId(), student1);
        lenderMap.put(student2.getId(), student2);
        lenderMap.put(student3.getId(), student3);
        lenderMap.put(student4.getId(), student4);
        lenderMap.put(student5.getId(), student5);
        lenderMap.put(teacher1.getId(), teacher1);
        lenderMap.put(teacher1.getId(), teacher1);

        BookShelf bookShelf = new BookShelf(initialBooks);
        Library library = new Library(bookShelf, lenderMap);

        login(library);
    }

    public static void mainMenu(Library library, Lender lender) {
        Scanner src = new Scanner(System.in);
        while (true) {
            System.out.println("\n==== MAIN MENU ====");
            System.out.println("1 -> View Books Available on shelf");
            System.out.println("2 -> Borrow Book");
            System.out.println("3 -> Books with Me");
            System.out.println("4 -> Return Book");
            System.out.println("5 -> Logout and Login new User");
            System.out.println("0 -> Quit");
            System.out.print("Choose a task: ");

            int userInput = src.nextInt();

            switch (userInput) {
                case 1 -> printAvailableBook(library.getBookShelf().getAvailableBooks());
                case 2 -> getBookByTitle(library,lender);
                case 3 -> lender.printBooksWithMe();
                case 4 -> returnBookMenu(library, lender);
                case 5-> loginNewUser(library);
                case 0 -> {
                    System.out.println("Exiting system...");
                    return;
                }
                default -> System.out.println("Invalid input, try again.");
            }
        }
    }

    public static void login(Library library) {
        Scanner src = new Scanner(System.in);
        log.info("System started. Awaiting user login...");
        System.out.println("========WELCOME=========");
        System.out.println("1-> Login as Admin");
        System.out.println("2-> Login as Lender");
        System.out.println("Enter Choice");

        int input = src.nextInt();
        src.nextLine();

        switch (input) {
            case 1 -> {
                System.out.print("Please ADMIN your ID: ");
                String userInput = src.nextLine();
                if (userInput.equalsIgnoreCase("admin")) {
                    log.info("Admin login successful.");
                    callAdminMenu(library);
                } else {
                    log.warn("Failed admin login attempt with ID: {}", userInput);
                }
            }
            case 2 -> {
                System.out.print("Please Enter your ID: ");
                String userInput = src.nextLine();
                var lender = library.getLenderById(userInput);
                if (lender != null) {
                    log.info("Lender login successful: {} ({})", lender.getFirstName(), lender.getId());
                    mainMenu(library, lender);
                } else {
                    log.warn("Login failed. No user found with ID: {}", userInput);
                    login(library);
                }
            }
            default -> log.warn("Invalid login option selected: {}", input);
        }
    }

    public static void loginNewUser(Library library){
        login(library);
    }

    public static void getBookByTitle(Library library, Lender lender) {
        Scanner src = new Scanner(System.in);
        System.out.print("Enter the title of the book you want to borrow: ");
        String userInput = src.nextLine();
        Book book = library.getBookShelf().getBookByTitle(userInput);
        if (book != null) {
            library.borrowBook(lender, book);
        } else {
            System.out.println("Book not found!");
        }
    }

    public static void callAdminMenu(Library library) {
        Scanner src = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== ADMIN MENU ====");
            System.out.println("1 -> View Books Available on shelf (with copies)");
            System.out.println("2 -> Lending Histories");
            System.out.println("3 -> Current Lenders (who have books)");
            System.out.println("4 -> Borrow Requests");
            System.out.println("5 -> Logout and Login new User");
            System.out.println("0 -> Quit");
            System.out.print("Choose a task: ");

            int choice = src.nextInt();
            src.nextLine(); // consume newline

            switch (choice) {
                case 1 -> library.getBookShelf().printAvailableBooksWithCopies();
                case 2 -> library.printLendingHistories();
                case 3 -> library.printCurrentLenders();
                case 4 -> library.printBorrowRequests();
                case 5 -> loginNewUser(library);
                case 0 -> {
                    System.out.println("Exiting system...");
                    return;
                }
                default -> System.out.println("Invalid input, try again.");
            }
        }
    }


    //filter books, by genre, by publication year
    public static void getBooksByGenre(Library library, Lender lender) {
        Scanner src = new Scanner(System.in);
        System.out.print("Enter genre: ");
        String genre = src.nextLine();
        List<Book> books = library.getBookShelf().getBooksByGenre(genre);
        if (books.isEmpty()) {
            System.out.println("No books found for this genre.");
        } else {
            books.forEach(Book::getBookDetails);
        }
    }

    public static void returnBookMenu(Library library, Lender lender) {
        Scanner src = new Scanner(System.in);
        System.out.print("Enter the title of the book to return: ");
        String title = src.nextLine();
        Book book = lender.getBooksBorrowed()
                .stream()
                .filter(b -> b.title().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);

        if (book != null) {
            library.returnBook(lender, book);
        } else {
            System.out.println("You don’t have this book borrowed.");
        }
    }
}
