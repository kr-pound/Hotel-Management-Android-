package com.example.hotelmanagement.calculator_room_package;

import com.example.hotelmanagement.calculator.AbstractRoom;

public class PackageBath extends RoomPackage {

    double price = 100;

    public PackageBath(AbstractRoom abstractRoom) {
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
