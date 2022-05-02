package com.example.hotelmanagement.calculator;

import androidx.annotation.NonNull;

public class Joint extends AbstractRoom {

    String imagePath = "@drawable/room_joint";

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public double getCost() {
        price = 1923;
        return price;
    }

    @NonNull
    @Override
    public String toString() {
        return "Joint Room";
    }
}
