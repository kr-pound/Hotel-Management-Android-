package com.example.hotelmanagement.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hotelmanagement.collection.UserCollection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserDatabaseHandler extends DatabaseHandler {

    String refPath = "User";
    String orderRefPath = "username";

    private static UserDatabaseHandler uniqueInstance;

    private final UserCollection userCollection = UserCollection.getInstance();

    private UserDatabaseHandler() {
        // get reference to the path 'Room' class
        databaseReference = db.getReference(refPath);
    }

    public static synchronized UserDatabaseHandler getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new UserDatabaseHandler();
        }
        return uniqueInstance;
    }

    public void addAccount(String fullName, String username, String password, String gender) {
        User user = new User(fullName, username, password, gender);
        databaseReference.push().setValue(user);
        fetchUserCollection();
    }

    public void deleteAccount(String username) {

        getQuery(orderRefPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key;
                User user;

                // For each child in dataSnapshot
                for (DataSnapshot child : snapshot.getChildren()) {
                    key = child.getKey();
                    user = child.getValue(User.class);

                    // If username equal to the received one --> remove that child
                    assert user != null;
                    if (user.getUsername().equals(username)) {
                        assert key != null;
                        databaseReference.child(key).removeValue();
                    }
                }

                fetchUserCollection();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Debug-deleteAccount", "Cancelled Remove");
            }
        });
    }

    public void updateAccountInfo(String fullName, String username, String password,
                                  String gender, String accountType) {
        getQuery(orderRefPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key;
                User user;

                // For each child in dataSnapshot
                for (DataSnapshot child : snapshot.getChildren()) {
                    key = child.getKey();
                    user = child.getValue(User.class);

                    // If username equal to the received one --> remove that child
                    assert user != null;
                    if (user.getFullName().equals(fullName)) {
                        assert key != null;
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("fullName", user.getFullName());
                        hashMap.put("username", username);
                        hashMap.put("password", password);
                        hashMap.put("gender", gender);
                        hashMap.put("accountType", accountType);

                        databaseReference.child(key).updateChildren(hashMap);
                    }
                }

                fetchUserCollection();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Debug-updateAccountInfo", "Cancelled Update");
            }
        });
    }

    // Set User accountType
    public void approveUser(String username, String accountType) {

        getQuery().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key;
                User user;

                // For each child in dataSnapshot
                for (DataSnapshot child : snapshot.getChildren()) {
                    key = child.getKey();
                    user = child.getValue(User.class);

                    // If roomID equal to ID received --> change the hashMap
                    assert user != null;
                    if (user.getUsername() == username) {
                        assert key != null;

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("fullName", user.getFullName());
                        hashMap.put("username", user.getUsername());
                        hashMap.put("password", user.getPassword());
                        hashMap.put("gender", user.getGender());
                        hashMap.put("accountType", accountType);

                        databaseReference.child(key).updateChildren(hashMap);
                    }
                }

                fetchUserCollection();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Debug-approveUser", "Cancelled Update");
            }
        });
    }

    /* =========================================================== */

    // Pull all users from Database
    public void fetchUserCollection() {
        getQuery(orderRefPath).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userCollection.clearAllUsers();
                User user;

                for (DataSnapshot child : snapshot.getChildren()) {
                    user = child.getValue(User.class);
                    userCollection.addUser(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Debug-fetchUserCol", "Cancelled Fetching");
            }
        });
    }

}
