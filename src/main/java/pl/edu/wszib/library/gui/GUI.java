package pl.edu.wszib.library.gui;

import org.apache.commons.codec.digest.DigestUtils;
import pl.edu.wszib.library.database.UserDAO;
import pl.edu.wszib.library.database.BookDAO;
import pl.edu.wszib.library.model.Book;
import pl.edu.wszib.library.model.User;
import pl.edu.wszib.library.root.Auth;

import java.util.Scanner;

public class GUI {
    private static final Scanner scanner = new Scanner(System.in);
    final Auth auth = Auth.getInstance();
    private static final GUI instance = new GUI();

    private GUI() {
    }

    public static String showLoginRegister(){
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        return scanner.nextLine();
    }
    public String showMenu(){
        System.out.println("1. List of Books");

        System.out.println("2. Find a book");

        System.out.println("3. Rent a Book");

        System.out.println("4. List of rented books");

        System.out.println("5. List of overdue books");

        System.out.println("6. Log Out");

        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
            System.out.println("7. Add new book");
        }

        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
            System.out.println("8. Set User as Admin");
        }

        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
            System.out.println("9. List of users and admins");
        }

        return scanner.nextLine();
    }

    public static void listOfAllBooks(){
        BookDAO bookDAO = BookDAO.getInstance();
        bookDAO.getAllBooks();
    }
    public static void findBook(){
        BookDAO bookDAO = BookDAO.getInstance();
        System.out.println("Please enter book title or author or ISBN");
        bookDAO.printAvailableBooks(scanner.nextLine());
    }

    public static String readBook() {
        System.out.println("Please enter book title or author or ISBN");
        return scanner.nextLine();
    }

    public static Book readNewBookData() {
        System.out.println("Adding New Book");
        System.out.println("title");
        String title = scanner.nextLine();
        System.out.println("author");
        String author = scanner.nextLine();
        System.out.println("ISBN");
        String ISBN = scanner.nextLine();

        return new Book(title, author, ISBN, false);
    }

    public static void listOfRentedBooks() {
        BookDAO bookDAO = BookDAO.getInstance();
        bookDAO.printRentedBooks();
    }

    public static void listOfOverDueBooks() {
        BookDAO bookDAO = BookDAO.getInstance();
        bookDAO.printOverdueBooks();
    }

    public static void listOfUsers(){
        UserDAO userDAO = UserDAO.getInstance();
        userDAO.getUser();
        System.out.println("\n");
    }

    public static User readLoginAndPassword() {
        User user = new User();
        System.out.println("Login:");
        user.setLogin(scanner.nextLine());
        System.out.println("Password:");
        user.setPassword(scanner.nextLine());
        return user;
    }

    public static User readNewLoginAndPassword() {
        User user = new User();
        System.out.println("Name:");
        user.setName(scanner.nextLine());
        System.out.println("Surname:");
        user.setSurname(scanner.nextLine());
        System.out.println("Login:");
        user.setLogin(scanner.nextLine());
        System.out.println("Password:");
        user.setPassword(DigestUtils.md5Hex(scanner.nextLine() + Auth.getInstance().getSeed()));
        user.setRole(User.Role.USER);
        return user;
    }

    public static GUI getInstance() {
        return instance;
    }
}