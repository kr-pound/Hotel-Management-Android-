package com.example.hotelmanagement.calculator;

// Factory
public class RoomFactory {

    public AbstractRoom getAbstractRoom(String roomType) {
        switch (roomType) {
            case "Standard":
                return new Standard();
            case "Joint":
                return new Joint();
            case "Connecting":
                return new Connecting();
            case "Deluxe":
                return new Deluxe();
            case "Accessible":
                return new Accessible();
            case "Apartment Style":
                return new ApartmentStyle();
            case "Suite":
                return new Suite();

            default:
                return null;
        }
    }

}
