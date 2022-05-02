package com.example.hotelmanagement.room_management;


import com.example.hotelmanagement.calculator.AbstractRoom;
import com.example.hotelmanagement.calculator.RoomFactory;
import com.example.hotelmanagement.data.RoomDatabaseHandler;

public abstract class RoomStaffTools {

    RoomDatabaseHandler roomDatabaseHandler = RoomDatabaseHandler.getInstance();

    // Price Calculator & Abstract Room Factory
    private final RoomFactory roomFactory = new RoomFactory();

    // Get Info from Decorator
    public AbstractRoom getRoomInfo(String roomType) {
        return roomFactory.getAbstractRoom(roomType);
    }

    public void fetchAllRooms() {
        roomDatabaseHandler.fetchRoomCollection();
    }

}
