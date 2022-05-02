package com.example.hotelmanagement.collection;

import com.example.hotelmanagement.data.User;

import java.util.ArrayList;

// Singleton
public class UserCollection {

    private static UserCollection uniqueInstance;
    private ArrayList<User> users = new ArrayList<>();
    private User currentLoginUser;

    private UserCollection() {
    }

    public static synchronized UserCollection getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new UserCollection();
        }
        return uniqueInstance;
    }

    public User getCurrentLoginUser() {
        return currentLoginUser;
    }

    public void setCurrentLoginUser(User currentLoginUser) {
        this.currentLoginUser = currentLoginUser;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void clearAllUsers() {
        users.clear();
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

}
