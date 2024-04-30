package com.oop.room_booking.Request;

public class UserIDRequest {
    private int userID;

    public UserIDRequest(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
