package pl.edu.wszib.jdbc.model;

public class Book {
    private Integer id;
    private String title;
    private String author;
    private String ISBN;
    private Boolean rent;
    public Book(String title, String author, String ISBN, Boolean rent) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.rent = rent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Boolean isRent() {
        return rent;
    }

    public void setRent(Boolean rent) {
        this.rent = rent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Book Information:\n");
        sb.append("Title: ").append(title).append("\n");
        sb.append("Author: ").append(author).append("\n");
        sb.append("ISBN: ").append(ISBN).append("\n");
        sb.append("Is Rented: ").append(rent).append("\n");
        return sb.toString();
    }
}
