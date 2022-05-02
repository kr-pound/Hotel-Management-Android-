package com.example.hotelmanagement.room_management;

public class RoomOrganizerTools extends RoomStaffTools {

    public void reserveRoom(int roomID, double price) {
        roomDatabaseHandler.setRoomStatus(roomID, false, price);
    }

    public void returnRoom(int roomID) {
        roomDatabaseHandler.setRoomStatus(roomID, true, 0);
    }

}
