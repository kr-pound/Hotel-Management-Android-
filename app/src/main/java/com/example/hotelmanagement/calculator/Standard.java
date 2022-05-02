package com.example.hotelmanagement.calculator;

import androidx.annotation.NonNull;

public class Standard extends AbstractRoom {

    String imagePath = "@drawable/room_standard";

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public double getCost() {
        price = 1480;
        return price;
    }

    @NonNull
    @Override
    public String toString() {
        return "Standard Room";
    }
}
