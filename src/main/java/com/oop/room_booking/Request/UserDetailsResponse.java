package com.oop.room_booking.Request;

public class UserDetailsResponse {

    private String name;
    private int userID;
    private String email;

    // Getters and setters
    // You can generate these using your IDE or write them manually

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

