package com.example.hotelmanagement.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public abstract class DatabaseHandler {

    // Reference to Database
    DatabaseReference databaseReference;
    FirebaseDatabase db = FirebaseDatabase.getInstance(
            "https://hotelmanagement-d71e4-default-rtdb.asia-southeast1.firebasedatabase.app/");

    // get all the children
    public Query getQuery(String childPath) {
        return databaseReference.orderByChild(childPath);
        //return databaseReference.orderByKey();
    }

    // for login username checking
    public Query getQuery() {
        return getQuery("username");
    }

}
