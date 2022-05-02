package com.example.hotelmanagement.collection;

import com.example.hotelmanagement.data.Room;

import java.util.ArrayList;

// Singleton
public class RoomCollection {

    private static RoomCollection uniqueInstance;
    private ArrayList<Room> rooms = new ArrayList<>();

    private RoomCollection() {
    }

    public static synchronized RoomCollection getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new RoomCollection();
        }
        return uniqueInstance;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void clearAllRooms() {
        rooms.clear();
    }

    public ArrayList<Room> getAllRooms() {
        return rooms;
    }

}
