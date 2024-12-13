package com.example.todolist.ViewModels;

public class LoginVM {
    private String username;
    private String password;
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
    public LoginVM() {
    }
    public LoginVM(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
