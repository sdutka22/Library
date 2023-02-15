package pl.edu.wszib.jdbc.database;

import pl.edu.wszib.jdbc.model.*;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookDB {
    private static final BookDB instance = new BookDB();

    private final List<Book> books = new ArrayList<>();

    private BookDB(){
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "0446310786"));
        books.add(new Book("1984", "George Orwell", "0451524934"));
        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", "0316769487"));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "0743273567"));
        books.add(new Book("Pride and Prejudice", "Jane Austen", "0141439513"));
        books.add(new Book("The Hobbit", "J.R.R. Tolkien", "054792822X"));
        books.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", "0544003411"));
        books.add(new Book("The Da Vinci Code", "Dan Brown", "0307474275"));
        books.add(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "0747532699"));
        books.add(new Book("The Hunger Games", "Suzanne Collins", "0439023483"));
    }

    public void printAvailableBooks(String identifier) {
        String regex = ".*" + identifier.toLowerCase() + ".*";
        this.books.stream()
                .filter(book -> (book.getTitle().toLowerCase().matches(regex) ||
                        book.getAuthor().toLowerCase().matches(regex) ||
                        book.getISBN().toLowerCase().matches(regex)))
                .map(Book::toString)
                .forEach(System.out::println);
    }

    public void printRentedBooks() {
        boolean hasRentedBooks = false;
        for (Book book : this.books) {
            if (book.isRent()) {
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("ISBN: " + book.getISBN());
                System.out.println("Rented By: " + book.getUserId());
                System.out.println();
            }
        }
        if (!hasRentedBooks) {
            System.out.println("There are no rented books.");
        }
    }

    public void printOverdueBooks() {
        LocalDate currentDate = LocalDate.now();
        for (Book book : this.books) {
            if (book.isRent() && LocalDate.parse(book.getReturnDate()).isBefore(currentDate)) {
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("ISBN: " + book.getISBN());
                System.out.println("Rented By: " + book.getUserId());
                LocalDate returnDate = LocalDate.parse(book.getReturnDate());
                long daysOverdue = ChronoUnit.DAYS.between(returnDate, currentDate);
                System.out.println("Days Overdue: " + daysOverdue);
                System.out.println();
            }
        }
    }

    public boolean rentBook(String identifier) {
        User user = new User();
        Scanner scanner = new Scanner(System.in);
        for (Book book : this.books) {
            if ((book.getTitle().equals(identifier) || book.getAuthor().equals(identifier) || book.getISBN().equals(identifier)) && !book.isRent()) {
                book.setRent(true);
                book.setReturnDate("Some return date"); // set the return date here

                book.setUserId(user.getName() + " " + user.getSurname()); // set the name and surname of the person renting the book

                LocalDate today = LocalDate.now();
                //LocalDate date15DaysAgo = today.minusDays(15); do testowania czy dziala liczenie po terminie
                LocalDate returnDate = today.plusDays(14);

                book.setReturnDate(returnDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
                return true;
            }
        }
        return false;
    }

    public void addNewBook(Book book) {
        books.add(book);
    }


    public static BookDB getInstance() {
        return instance;
    }
}