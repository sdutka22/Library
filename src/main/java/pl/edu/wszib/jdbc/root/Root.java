package pl.edu.wszib.jdbc.root;

import pl.edu.wszib.jdbc.model.User;
import pl.sdutka.electronic.shop.gui.GUI;

import java.util.Objects;
import java.util.Scanner;

import static pl.sdutka.electronic.shop.gui.GUI.showLoginRegister;


public class Root {
    private static final Scanner scanner = new Scanner(System.in);
    final ProductDB productDB = ProductDB.getInstance();
    final pl.sdutka.electronic.shop.root.Auth auth = pl.sdutka.electronic.shop.root.Auth.getInstance();
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
                    if(!pl.sdutka.electronic.shop.root.Auth.validate(user)){
                        System.out.println("Wrong login or password entered. Valid login and password consist of at least 5 letters,");
                    }else{
                        if(pl.sdutka.electronic.shop.root.Auth.doesExist(user, this.auth.userDB)) {
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
                    case "1" -> GUI.listOfDevices();
                    case "2" -> GUI.showBuyResult(productDB.buyDevice(GUI.readProduct()));
                    case "3" -> {
                        this.auth.logOut();
                        isRunning = false;
                    }
                    case "4" -> {
                        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
                            productDB.increaseAmountOfExistingDevice(GUI.readAmountOfProductToBuy());
                        }
                    }
                    case "5" -> {
                        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
                            productDB.addNewDevice(GUI.readNewDeviceData());
                        }
                    }
                    case "6" -> {
                        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
                            System.out.println("Podaj login");
                            String login = scanner.nextLine();
                            this.auth.userDB.setUserAsAdmin(login, GUI.readUsertoAdmin());
                        }
                    }
                    case "7" -> {
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