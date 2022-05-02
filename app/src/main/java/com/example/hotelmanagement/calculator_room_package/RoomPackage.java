package com.example.hotelmanagement.calculator_room_package;

import com.example.hotelmanagement.calculator.AbstractRoom;

// Decorator
public abstract class RoomPackage extends AbstractRoom {

    public AbstractRoom abstractRoom;

    public abstract double getCost();

}
