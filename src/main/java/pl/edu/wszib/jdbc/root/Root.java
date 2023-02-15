package pl.edu.wszib.jdbc.root;

import pl.edu.wszib.jdbc.model.User;
import pl.edu.wszib.jdbc.database.BookDB;
import pl.edu.wszib.jdbc.gui.GUI;
import java.util.Scanner;

import static pl.edu.wszib.jdbc.gui.GUI.showLoginRegister;

public class Root {
    private static final Scanner scanner = new Scanner(System.in);
    final BookDB bookDB = BookDB.getInstance();
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
                        if(pl.edu.wszib.jdbc.root.Auth.doesExist(user, this.auth.userDB)) {
                            System.out.println("An account with the given login already exists");
                        }else{
                            this.auth.userDB.addNewUser(user);
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
                    case "1" -> GUI.listOfDevices(); //List of Books
                    case "2" -> GUI.showBuyResult(bookDB.rentBook(GUI.readBook()));

                    case "3" -> { //logout
                        this.auth.logOut();
                        isRunning = false;
                    }
                    case "4" -> { //Adding new books into our list
                        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
                            bookDB.addNewBook(GUI.readNewBookData());
                        }
                    }
                    case "5" -> { //Setting user as Admin
                        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
                            System.out.println("Podaj login");
                            String login = scanner.nextLine();
                            this.auth.userDB.setUserAsAdmin(login);
                        }
                    }
                    case "6" -> { //Listing existing users
                        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
                            GUI.listOfUsers();
                        }
                    }
                    case "7" -> {
                        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
                            GUI.listOfRentedBooks(); //List of rented books
                        }
                    }
                    case "8" -> {
                        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
                            GUI.listOfOverDueBooks(); //List of overdue books
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