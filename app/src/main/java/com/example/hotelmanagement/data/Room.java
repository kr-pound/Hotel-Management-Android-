package com.example.hotelmanagement.data;

public class Room {

    private int id;
    private String type;

    private boolean availability = true;
    private double price = 0;

    public Room(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public Room() {
    }

    /* Getter & Setter */
    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean status) {
        this.availability = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
