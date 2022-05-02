package com.example.hotelmanagement.room_management;

public class UserManagementTools extends UserStaffTools {

    public void removeUser(String username) {
        userDatabaseHandler.deleteAccount(username);
    }

    public void updateUserInfo(String fullName, String username, String password, String gender, String accountType) {
        userDatabaseHandler.updateAccountInfo(fullName, username, password, gender, accountType);
    }

}
