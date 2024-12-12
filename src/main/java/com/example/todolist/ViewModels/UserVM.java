package com.example.todolist.ViewModels;

public class UserVM {
    private String fullName;
    private String role;
    private String username;
    private String password;
    private String email;
    private Integer userId; 

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Constructor, getters, and setters
    public UserVM(Integer userId, String fullName, String role, String username, String password, String email) {
        this.fullName = fullName;
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
