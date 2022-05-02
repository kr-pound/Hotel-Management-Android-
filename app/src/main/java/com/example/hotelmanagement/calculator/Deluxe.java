package com.example.hotelmanagement.calculator;

import androidx.annotation.NonNull;

public class Deluxe extends AbstractRoom {

    String imagePath = "@drawable/room_deluxe";

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public double getCost() {
        price = 2243;
        return price;
    }

    @NonNull
    @Override
    public String toString() {
        return "Deluxe Room";
    }
}
