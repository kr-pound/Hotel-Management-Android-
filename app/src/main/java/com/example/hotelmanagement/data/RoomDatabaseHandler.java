package com.example.hotelmanagement.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hotelmanagement.collection.RoomCollection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RoomDatabaseHandler extends DatabaseHandler {

    private String refPath = "Room";
    private String orderRefPath = "id";

    private static RoomDatabaseHandler uniqueInstance;

    private final RoomCollection roomCollection = RoomCollection.getInstance();

    private RoomDatabaseHandler() {
        // get reference to the path 'Room' class
        databaseReference = db.getReference(refPath);
    }

    public static synchronized RoomDatabaseHandler getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new RoomDatabaseHandler();
        }
        return uniqueInstance;
    }

    public void addRoom(int roomID, String type) {
        Room room = new Room(roomID, type);
        databaseReference.push().setValue(room);
        fetchRoomCollection();
    }

    public void deleteRoom(int roomID) {

        getQuery(orderRefPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key;
                Room room;

                // For each child in dataSnapshot
                for (DataSnapshot child : snapshot.getChildren()) {
                    key = child.getKey();
                    room = child.getValue(Room.class);

                    // If roomID equal to ID received --> remove that child
                    assert room != null;
                    if (room.getID() == roomID) {
                        assert key != null;
                        databaseReference.child(key).removeValue();
                    }
                }

                fetchRoomCollection();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Debug-deleteRoom", "Cancelled Remove");
            }
        });
    }

    public void setRoomStatus(int roomID, boolean availability,double price) {

        getQuery(orderRefPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key;
                Room room;

                // For each child in dataSnapshot
                for (DataSnapshot child : snapshot.getChildren()) {
                    key = child.getKey();
                    room = child.getValue(Room.class);

                    // If roomID equal to ID received --> change the hashMap
                    assert room != null;
                    if (room.getID() == roomID) {
                        assert key != null;

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("id", room.getID());
                        hashMap.put("type", room.getType());
                        hashMap.put("availability", availability);
                        hashMap.put("price", price);

                        databaseReference.child(key).updateChildren(hashMap);
                    }
                }

                fetchRoomCollection();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Debug-setRoomStatus", "Cancelled Update");
            }
        });
    }

    /* =========================================================== */

    // Pull all rooms from Database
    public void fetchRoomCollection() {
        getQuery(orderRefPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomCollection.clearAllRooms();
                Room room;

                for (DataSnapshot child : snapshot.getChildren()) {
                    room = child.getValue(Room.class);
                    roomCollection.addRoom(room);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Debug-fetchRoomCol", "Cancelled Fetching");
            }
        });
    }

}
