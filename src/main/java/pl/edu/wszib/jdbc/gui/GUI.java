package pl.edu.wszib.jdbc.gui;

import org.apache.commons.codec.digest.DigestUtils;
import pl.edu.wszib.jdbc.database.BookDB;
import pl.edu.wszib.jdbc.database.UserDB;
import pl.edu.wszib.jdbc.model.User;
import pl.edu.wszib.jdbc.model.Book;
import pl.edu.wszib.jdbc.root.Auth;
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

        System.out.println("2. Rent a Book");

        System.out.println("3. Log Out");

        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
            System.out.println("4. Add new book");
        }

        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
            System.out.println("5. Set User as Admin");
        }

        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
            System.out.println("6. List of users and admins");
        }

        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
            System.out.println("7. List of rented books");
        }
        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
            System.out.println("8. List of overdue books");
        }

        return scanner.nextLine();
    }
    public static void listOfDevices(){//list of Books
        BookDB bookDB = BookDB.getInstance();
        System.out.println("Please enter book title or author or ISBN");
        bookDB.printAvailableBooks(scanner.nextLine());
    }

    public static String readBook() {
        System.out.println("Please enter book title or author or ISBN");
        return scanner.nextLine();
    }

    public static void showBuyResult(boolean result) {
        if(!result) {
            System.out.println("This book does not exist or it is rent now");
        }
    }

    public static Book readNewBookData() {
        System.out.println("Adding New Book");
        System.out.println("title");
        String title = scanner.nextLine();
        System.out.println("author");
        String author = scanner.nextLine();
        System.out.println("ISBN");
        String ISBN = scanner.nextLine();

        return new Book(title, author, ISBN);
    }

    public static void listOfRentedBooks() {
        BookDB bookDB = BookDB.getInstance();
        bookDB.printRentedBooks();
    }

    public static void listOfOverDueBooks() {
        BookDB bookDB = BookDB.getInstance();
        bookDB.printOverdueBooks();
    }

    public static void listOfUsers(){
        UserDB userDB = UserDB.getInstance();
        userDB.getUser();
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
        return user;
    }

    public static GUI getInstance() {
        return instance;
    }
}