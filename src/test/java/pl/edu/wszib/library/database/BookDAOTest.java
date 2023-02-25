package pl.edu.wszib.library.database;

import org.junit.jupiter.api.*;
import pl.edu.wszib.library.model.Book;
import pl.edu.wszib.library.model.User;

public class BookDAOTest {
    @BeforeEach
    public void prepare(){
        System.out.println("Przygotowanie do testu");
    }

    @AfterEach
    public void clean(){
        System.out.println("czyszczenie po teście");
    }

    @BeforeAll
    public static void prepareAll(){
        System.out.println("Przygotowanie przed wszystkimi testami ");
    }

    @AfterAll
    public static void clearAfterAll(){
        System.out.println("Czyszczenie po wszystkich testach");
    }
    @Test
    public void testSuccessdRentBook() {
        BookDAO bookDAO = BookDAO.getInstance();
        Book book = new Book("The Great Gatsby", "F. Scott Fitzgerald", "0743273567", false);
        User user = new User("admin","admin");
        boolean rentingBook = bookDAO.rentBook(book.getTitle(), user);
        Assertions.assertTrue(rentingBook);
    }
    @Test
    public void testFailedRentBook() {
        BookDAO bookDAO = BookDAO.getInstance();
        Book book = new Book("Test Book", "Test Author", "1234567890", false);
        User user = new User("admin","admin");
        boolean rentingBook = bookDAO.rentBook(book.getTitle(), user);
        Assertions.assertFalse(rentingBook);
    }

    @Test
    public void testAddNewBook() {
        BookDAO bookDAO = BookDAO.getInstance();
        Book book = new Book("Test Book22", "Test Authorsd", "12345678908000", false);
        Book insertedBook = bookDAO.addNewBook(book);
        Assertions.assertEquals(book.getTitle(), insertedBook.getTitle());
    }

}
