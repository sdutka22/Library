package pl.edu.wszib.jdbc;

import pl.edu.wszib.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class App {
    public static Connection connection;
    public static void main(String[] args) {
        connect();
        User user = new User(3, "zibi", "zibi", User.Role.USER);
//        saveUser2(user);
        user.setLogin("zbyszek");
        user.setPassword("zbyszek1");
        user.setRole(User.Role.ADMIN);
//        updateUser(user);
//        deleteUser(user.getId());
        List<User> users = getAllUsers();
        System.out.println(users);
        getUserById(7).ifPresentOrElse(System.out::println, () -> System.out.println("Nie ma takiego usera"));
        close();
    }
    public static void connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3400/library",
                    "root",
                    "");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(){
        try {
            connection.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void saveUser(User user){
        try {
            String sql = new StringBuilder()
                    .append("INSERT INTO tuser (login, password, role) VALUES ('")
                    .append(user.getLogin())
                    .append("', '")
                    .append(user.getPassword())
                    .append("', '")
                    .append(user.getRole().toString())
                    .append("');")
                    .toString();

            Statement statement = connection.createStatement();
            statement.execute(sql);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void saveUser2(User user){
        try {
            String sql = "INSERT INTO tuser (login, password, role) VALUES (?,?,?)";

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole().toString());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            user.setId(rs.getInt(1));
//            ps.clearParameters();
//            ps.setString(1, "janusz");
//            ps.setString(2, "janusz11");
//            ps.setString(3, User.Role.ADMIN.toString());
//            ps.executeUpdate();
            ;        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void updateUser(User user){
        try {
            String sql = "UPDATE tuser SET login = ?, password = ?, role = ? WHERE id = ?";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole().toString());
            ps.setInt(4, user.getId());

            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void deleteUser(int id){
        try {
            String sql = "DELETE FROM tuser WHERE id = ?";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static List<User> getAllUsers(){
        List<User> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tuser";

            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(new User(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        User.Role.valueOf(rs.getString("role"))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static Optional<User> getUserById(int id){
        try{
            String sql = "SELECT * FROM tuser WHERE id = ?";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return Optional.of(new User(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        User.Role.valueOf(rs.getString("role"))));
            }
        }catch (SQLException e){
            System.out.println("Error !!!!");
        }
        return Optional.empty();
    }
}
