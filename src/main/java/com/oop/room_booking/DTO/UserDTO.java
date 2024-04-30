package com.oop.room_booking.DTO;

public class UserDTO {
    private int userID;
    private String email;
    private String name;

    // Constructors, getters, and setters
    public UserDTO(int userID, String email, String name) {
        this.userID = userID;
        this.email = email;
        this.name = name;
    }
    public UserDTO(int userID) {
        this.userID = userID;
    }

    // Getters and setters
    public int getUserID() {
        return userID;
    }

    public void setUserid(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
