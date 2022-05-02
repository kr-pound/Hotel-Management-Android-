package com.example.hotelmanagement.calculator;

import androidx.annotation.NonNull;

public class ApartmentStyle extends AbstractRoom {

    String imagePath = "@drawable/room_apartment_style";

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public double getCost() {
        price = 3485;
        return price;
    }

    @NonNull
    @Override
    public String toString() {
        return "Apartment Style Room";
    }
}
