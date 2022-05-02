package com.example.hotelmanagement.verification;

import com.example.hotelmanagement.collection.UserCollection;
import com.example.hotelmanagement.data.User;

public class VerificationService {

    UserCollection users = UserCollection.getInstance();

    // Verification
    public String verifyRegister(String fullName, String username, String password) {
        String description;
        if (fullName.length() < 4) {
            description = "Full Name should be 4 characters long";
            return description;
        } else if (username.length() < 4) {
            description = "Username should be 4 characters long";
            return description;
        } else if (password.length() < 8) {
            description = "Password should be 8 characters long";
            return description;
        }

        // Check that it duplicate or not
        if (duplicateChecking(username, fullName) != null) {
            return duplicateChecking(username, fullName);
        }

        return null;
    }

    private String duplicateChecking(String username, String fullName) {
        String description;
        for (User user : users.getAllUsers()) {
            if (user.getUsername().equals(username)) {
                description = "This username is already used";
                return description;
            }
            if (user.getFullName().equals(fullName)) {
                description = "This full name is already used";
                return description;
            }
        }
        return null;
    }

}
