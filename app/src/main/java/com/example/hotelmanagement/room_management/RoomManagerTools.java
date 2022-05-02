package com.example.hotelmanagement.room_management;

public class RoomManagerTools extends RoomStaffTools {

    public void addRoom(int roomID, String type) {
        roomDatabaseHandler.addRoom(roomID, type);
    }

    public void deleteRoom(int roomID) {
        roomDatabaseHandler.deleteRoom(roomID);
    }

}
