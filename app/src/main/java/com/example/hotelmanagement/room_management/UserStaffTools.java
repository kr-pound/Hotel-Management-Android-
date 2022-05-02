package com.example.hotelmanagement.room_management;

import com.example.hotelmanagement.data.UserDatabaseHandler;

public abstract class UserStaffTools {

    UserDatabaseHandler userDatabaseHandler = UserDatabaseHandler.getInstance();

    public void fetchAllUsers() {
        userDatabaseHandler.fetchUserCollection();
    }

}
