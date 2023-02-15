package pl.edu.wszib.jdbc.model;

import java.util.UUID;

public class User {
    private Integer id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private Role role;

    public User(){}
    public User(String name, String surname, String login, String password, Role role) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role {
        USER,
        ADMIN
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User Information:\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Surname: ").append(surname).append("\n");
        sb.append("login: ").append(login).append("\n");
        sb.append("Password: ").append(password).append("\n");
        sb.append("Role: ").append(role).append("\n");
        return sb.toString();
    }
}
