package com.example.hotelmanagement.calculator;

import androidx.annotation.NonNull;

public class Connecting extends AbstractRoom {

    String imagePath = "@drawable/room_connecting";

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public double getCost() {
        price = 2137;
        return price;
    }

    @NonNull
    @Override
    public String toString() {
        return "Connecting Room";
    }
}
