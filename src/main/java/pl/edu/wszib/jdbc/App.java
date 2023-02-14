package pl.edu.wszib.jdbc;

import java.sql.*;

public class App {
    public static Connection connection;
    public static void main(String[] args) {
        connect();
        User user = new User(1, "admin", "admin", User.Role.ADMIN);
        saveUser2(user);
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

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole().toString());

            ps.executeUpdate();
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
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
