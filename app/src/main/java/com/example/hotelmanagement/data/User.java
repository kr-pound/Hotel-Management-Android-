package com.example.hotelmanagement.data;

public class User {
    private String fullName;
    private String username, password;
    private String gender;

    private String accountType = "Temp";

    public User(String fullName, String username, String password, String gender) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public User() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
