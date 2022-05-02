package com.example.hotelmanagement.calculator;

import androidx.annotation.NonNull;

public class Suite extends AbstractRoom {

    String imagePath = "@drawable/room_suite";

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public double getCost() {
        price = 4160;
        return price;
    }

    @NonNull
    @Override
    public String toString() {
        return "Suite Room";
    }
}
