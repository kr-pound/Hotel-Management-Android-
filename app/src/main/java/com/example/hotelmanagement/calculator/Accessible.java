package com.example.hotelmanagement.calculator;

import androidx.annotation.NonNull;

public class Accessible extends AbstractRoom {

    String imagePath = "@drawable/room_accessible";

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public double getCost() {
        price = 2756;
        return price;
    }

    @NonNull
    @Override
    public String toString() {
        return "Accessible Room";
    }
}
