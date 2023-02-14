package pl.edu.wszib.jdbc.gui;

import pl.edu.wszib.jdbc.database.UserDB;
import pl.edu.wszib.jdbc.model.User;
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
        System.out.println("1. List of products");

        System.out.println("2. Buy Product");

        System.out.println("3. Log Out");

        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
            System.out.println("4. Increase amount of existing device");
        }

        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
            System.out.println("5. Add new device");
        }

        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
            System.out.println("6. Set User as Admin");
        }

        if(this.auth.getLoggedUser() != null && this.auth.getLoggedUser().getRole() == User.Role.ADMIN) {
            System.out.println("7. List of users and admins");
        }

        return scanner.nextLine();
    }
    public static void listOfDevices(){
        ProductDB productDB = ProductDB.getInstance();
        productDB.getDevices();
    }

    public static String[] readProduct() {
        String[] device = new String[2];
        System.out.println("Device name: ");
        String deviceName =scanner.nextLine();
        device[0] = deviceName;

        System.out.println("How much do you want to buy:");
        String deviceAmount =scanner.nextLine();
        device[1] = deviceAmount;

        return device;
    }

    public static void showBuyResult(boolean result) {
        if(!result) {
            System.out.println("This product does not exist or it is out of stock now");
        }
    }

    public static String[] readAmountOfProductToBuy(){
        String[] device = new String[2];
        System.out.println("Specify which product you want to add more pieces to");
        device[0] = scanner.nextLine();
        System.out.println("Enter how many pieces of the product you want to add");
        device[1] = scanner.nextLine();

        return device;
    }

    public static Device readNewDeviceData() {
        System.out.println("1. Laptop");
        System.out.println("2. SmartPhone");
        System.out.println("3. SmartWatch");
        System.out.println("4. Tablet");
        String choose = scanner.nextLine();

        System.out.println("Brand:");
        String brand = scanner.nextLine();

        System.out.println("Model:");
        String model = scanner.nextLine();

        System.out.println("Production year:");
        int productionYear = Integer.parseInt(scanner.nextLine());

        System.out.println("Color:");
        String color = scanner.nextLine();

        System.out.println("Price:");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.println("Products Amount:");
        int productsAmount = Integer.parseInt(scanner.nextLine());

        System.out.println("RAM:");
        int RAM = Integer.parseInt(scanner.nextLine());

        System.out.println("built in memory:");
        int builtInMemory = Integer.parseInt(scanner.nextLine());

        System.out.println("ScreenSize [cale]:");
        double screenSize = Double.parseDouble(scanner.nextLine());

        switch (choose) {
            case "1" -> {
                System.out.println("Processor:");
                String processor = scanner.nextLine();
                System.out.println("Graphic Card:");
                String graphicCard = scanner.nextLine();
                System.out.println("Operating System:");
                String operatingSystem = scanner.nextLine();
                return new Laptop(brand, model, productionYear, color, RAM, builtInMemory, processor, graphicCard, operatingSystem, screenSize, price, productsAmount);
            }
            case "2" -> {
                System.out.println("Camera:");
                int camera = Integer.parseInt(scanner.nextLine());
                return new SmartPhone(brand, model, productionYear, color, RAM, builtInMemory, screenSize, camera, price, productsAmount);
            }
            case "3" -> {

                return new SmartWatch(brand, model, productionYear, color, RAM, builtInMemory, screenSize, price, productsAmount);
            }
            case "4" -> {
                System.out.println("Camera:");
                int cameraTab = Integer.parseInt(scanner.nextLine());
                return new Tablet(brand, model, productionYear, color, RAM, builtInMemory, screenSize, cameraTab, price, productsAmount);
            }
        }

        return null;
    }

    public static void listOfUsers(){
        UserDB userDB = UserDB.getInstance();
        userDB.getUser();
        System.out.println("\n");
    }

    public static String readUsertoAdmin(){
        System.out.println("user ID:");

        return scanner.nextLine();
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