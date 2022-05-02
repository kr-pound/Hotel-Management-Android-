package com.example.hotelmanagement.calculator_room_package;

import com.example.hotelmanagement.calculator.AbstractRoom;

public class PackageWifi extends RoomPackage {

    double price = 200;

    public PackageWifi(AbstractRoom abstractRoom) {
        this.abstractRoom = abstractRoom;
    }

    @Override
    public String getImagePath() {
        return abstractRoom.getImagePath();
    }

    @Override
    public double getCost() {
        return abstractRoom.getCost() + price;
    }

}
