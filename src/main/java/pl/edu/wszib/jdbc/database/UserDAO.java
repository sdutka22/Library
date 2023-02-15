package pl.edu.wszib.jdbc.database;

import pl.edu.wszib.jdbc.model.User;

import java.sql.*;

public class UserDAO {
    private final Connection connection;
    private static final UserDAO instance = new UserDAO();

    public UserDAO(){
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

    public static UserDAO getInstance(){
        return instance;
    }


    public void getUser() {
        try {
            String sql = "SELECT * FROM tuser";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("login"),
                        rs.getString("password"),
                        User.Role.valueOf(rs.getString("role"))
                );
                System.out.println(user.toString());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByLogin(String login) {
        try {
            String sql = "SELECT * FROM tuser WHERE login = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);

            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return new User(
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("login"),
                        rs.getString("password"),
                        User.Role.valueOf(rs.getString("role")));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addNewUser(User user) {
        try {
            String sql = "INSERT INTO tuser (name, surname, login, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getLogin());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole().toString());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert new user.");
            }

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUserAsAdmin(String login){
        try {
            String sql = "UPDATE tuser SET role = 'ADMIN' WHERE login = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);

            ps.setString(1, login);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
