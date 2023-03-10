package pl.edu.wszib.library.database;

import pl.edu.wszib.library.model.Book;
import pl.edu.wszib.library.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class BookDAO {
    private final Connection connection;
    private static final BookDAO instance = new BookDAO();

    public BookDAO(){
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

    public static BookDAO getInstance(){
        return instance;
    }

    public void getAllBooks() {
        try {
            String sql = "SELECT * FROM tbook";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("ISBN"),
                        rs.getBoolean("rent"));
                System.out.println(book.toString());
            }
            System.out.println("\n");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void printAvailableBooks(String identifier) {
        try {
            String regex = "%" + identifier.toLowerCase() + "%";
            String sql = "SELECT * FROM tbook WHERE (LOWER(title) LIKE ? OR LOWER(author) LIKE ? OR LOWER(ISBN) LIKE ?)";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, regex);
            ps.setString(2, regex);
            ps.setString(3, regex);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("ISBN"),
                        rs.getBoolean("rent"));
                System.out.println(book.toString());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Book addNewBook(Book book) {
        try {
            String sql = "INSERT INTO tbook (title, author, ISBN, rent) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getISBN());
            ps.setBoolean(4, book.isRent());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding book: " + e.getMessage(), e);
        }
        return book;
    }


    public boolean rentBook(String identifier, User user) {

        try {
            String bookQuery = "SELECT * FROM tbook WHERE (title = ? OR author = ? OR ISBN = ?) AND rent = ?";
            PreparedStatement bookStatement = this.connection.prepareStatement(bookQuery);
            bookStatement.setString(1, identifier);
            bookStatement.setString(2, identifier);
            bookStatement.setString(3, identifier);
            bookStatement.setBoolean(4, false);
            ResultSet bookResult = bookStatement.executeQuery();

            if (!bookResult.next()) {
                return false;
            }

            String ISBN = bookResult.getString("ISBN");
            String userQuery = "SELECT * FROM tuser WHERE login = ?";
            PreparedStatement userStatement = this.connection.prepareStatement(userQuery);
            userStatement.setString(1, user.getLogin());
            ResultSet userResult = userStatement.executeQuery();

            if (!userResult.next()) {
                System.out.println("User not found.");
            }

            String login = userResult.getString("login");
            LocalDate returnDate = LocalDate.now().plusDays(14);

            String libraryQuery = "INSERT INTO tlibrary (login, ISBN, returnDate) VALUES (?, ?, ?)";
            PreparedStatement libraryStatement = this.connection.prepareStatement(libraryQuery);
            libraryStatement.setString(1, login);
            libraryStatement.setString(2, ISBN);
            libraryStatement.setString(3, returnDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            libraryStatement.executeUpdate();

            String updateQuery = "UPDATE tbook SET rent = ? WHERE ISBN = ?";
            PreparedStatement updateStatement = this.connection.prepareStatement(updateQuery);
            updateStatement.setBoolean(1, true);
            updateStatement.setString(2,  ISBN);
            updateStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void printRentedBooks() {
        try {
            String query = "SELECT tbook.title, tbook.author, tbook.ISBN, tuser.name, tuser.surname, tlibrary.returnDate " +
                    "FROM tbook JOIN tlibrary ON tbook.ISBN = tlibrary.ISBN " +
                    "JOIN tuser ON tuser.login = tlibrary.login WHERE tlibrary.returnDate >= ?";

            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                System.out.println("Book Information:");
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("ISBN: " + rs.getString("ISBN"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("surname: " + rs.getString("surname"));
                System.out.println("Days Left: " + (int) ChronoUnit.DAYS.between(LocalDate.now(),
                        rs.getDate("returnDate").toLocalDate()));
                System.out.println("\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void printOverdueBooks() {
        try {
            String query = "SELECT tbook.title, tbook.author, tbook.ISBN, tuser.name, tuser.surname, tlibrary.returnDate " +
                    "FROM tbook JOIN tlibrary ON tbook.ISBN = tlibrary.ISBN " +
                    "JOIN tuser ON tuser.login = tlibrary.login WHERE tlibrary.returnDate < ?";

            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                System.out.println("Book Information:");
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("ISBN: " + rs.getString("ISBN"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("surname: " + rs.getString("surname"));
                System.out.println("Days After sheduled return: " + (int) ChronoUnit.DAYS.between(rs.getDate("returnDate").toLocalDate(),
                        LocalDate.now()));
                System.out.println("\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}