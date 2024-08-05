package com.gabrielapredut.teachpoint.backend.model;

public class UserRegistrationRequest {

    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String roleName;

    // Default constructor
    public UserRegistrationRequest() {}

    // Parameterized constructor
    public UserRegistrationRequest(String username, String password, String name, String surname, String email, String roleName) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.roleName = roleName;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
