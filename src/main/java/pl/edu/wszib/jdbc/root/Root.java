package pl.edu.wszib.jdbc.root;

import pl.edu.wszib.jdbc.model.User;
import pl.edu.wszib.jdbc.database.BookDAO;
import pl.edu.wszib.jdbc.gui.GUI;
import java.util.Scanner;

import static pl.edu.wszib.jdbc.gui.GUI.showLoginRegister;

public class Root {
    private static final Scanner scanner = new Scanner(System.in);
    final BookDAO bookDAO = BookDAO.getInstance();
    final pl.edu.wszib.jdbc.root.Auth auth = pl.edu.wszib.jdbc.root.Auth.getInstance();
    final GUI gui = GUI.getInstance();
    private static final Root instance = new Root();
    private Root() {
    }
    public void run(){

        boolean isRunning = false;
        boolean isWorking = true;
        int counter = 0;

        while(isWorking) {
            switch(showLoginRegister()){
                case "1" -> {
                    User user = GUI.readNewLoginAndPassword();
                    if(!pl.edu.wszib.jdbc.root.Auth.validate(user)){
                        System.out.println("Wrong login or password entered. Valid login and password consist of at least 5 letters,");
                    }else{
                        if(pl.edu.wszib.jdbc.root.Auth.doesExist(user, this.auth.userDAO)) {
                            System.out.println("An account with the given login already exists");
                        }else{
                            this.auth.userDAO.addNewUser(user);
                        }
                    }
                }
                case "2" -> {
                    while(!isRunning && counter < 3) {
                        this.auth.authentication(GUI.readLoginAndPassword());
                        isRunning = this.auth.getLoggedUser() != null;
                        if(!isRunning) {
                            System.out.println("Not authorized !!");
                        }
                        counter++;
                    }
                    counter = 0;
                }
                case "3" -> {
                    isWorking = false;
                }
            }

            while (isRunning) {
                switch (this.gui.showMenu()) {
                    case "1" -> GUI.listOfAllBooks(); //List of all Books
                    case "2" -> GUI.findBook(); //find a book
                    case "3" -> {
                        if(bookDAO.rentBook(GUI.readBook(),this.auth.getLoggedUser())){
                            System.out.println("Book rented successfully");
                        }else{
                            System.out.println("Book not available for rent.");
                        }

                    }
                    case "4" -> GUI.listOfRentedBooks(); //List of rented books;
                    case "5" -> GUI.listOfOverDueBooks(); //List of overdue books
                    case "6" -> { //logout
                        this.auth.logOut();
                        isRunning = false;
                    }
                    case "7" -> { //Adding new books into our list
                        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
                            bookDAO.addNewBook(GUI.readNewBookData());
                        }
                    }
                    case "8" -> { //Setting user as Admin
                        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
                            System.out.println("Podaj login");
                            String login = scanner.nextLine();
                            this.auth.userDAO.setUserAsAdmin(login);
                        }
                    }
                    case "9" -> { //Listing existing users
                        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
                            GUI.listOfUsers();
                        }
                    }
                    default -> System.out.println("Wrong choose!! choose again");
                }
            }
        }
    }
    public static Root getInstance() {
        return instance;
    }
}