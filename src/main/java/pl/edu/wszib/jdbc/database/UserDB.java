//package pl.edu.wszib.jdbc.database;
//
//import pl.edu.wszib.jdbc.model.User;
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserDB {
//    private static final UserDB instance = new UserDB();
//    private final List<User> users = new ArrayList<>();
//
////    private UserDB(){
////        users.add(new User("admin", "admin", "admin", "eb0468abcd9f88e9844fd140fbb6acff"));
////        users.add(new User("janusz", "janusz", "janusz", "6fff9bb96e12805ea3ccb8ec27e8206f"));
////    }
//
//    public void getUser() {
//        users.stream().map(User::toString).forEach(System.out::println);
//    }
//
//    public User findByLogin(String login) {
//        return users.stream()
//                .filter(user -> login.equals(user.getLogin()))
//                .findAny()
//                .orElse(null);
//    }
//
//    public User setUserAsAdmin(String login) {
//        return this.users.stream()
//                .filter(user -> user.getLogin().equals(login))
//                .findFirst()
//                .map(user -> {
//                    user.setRole(User.Role.ADMIN);
//                    return user;
//                })
//                .orElse(null);
//    }
//
//    public void addNewUser(User user) {
//        users.add(user);
//    }
//
//    public static UserDB getInstance(){
//        return instance;
//    }
//}