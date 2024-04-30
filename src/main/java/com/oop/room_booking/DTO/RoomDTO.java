package com.oop.room_booking.DTO;

import com.oop.room_booking.Model.Booking;
import com.oop.room_booking.Model.Room;
import com.oop.room_booking.Request.UserIDRequest;

import java.util.ArrayList;
import java.util.List;

public class RoomDTO {
    private int roomID;
    private int capacity;
    private String roomName;
    private List<BookingDTO> bookings;

    public RoomDTO(int roomID, int capacity) {
        this.roomID = roomID;
        this.capacity = capacity;
        this.bookings = new ArrayList<>(); // Initialize bookings as an empty list
    }

    public RoomDTO(Room room) {
        this.roomID = room.getRoomID();
        this.capacity = room.getRoomCapacity();
        this.roomName = room.getRoomName();
        this.bookings = new ArrayList<>();
        if (room.getBookings() != null) {
            for (Booking booking : room.getBookings()) {
                this.bookings.add(new BookingDTO(
                        booking.getBookingID(),
                        booking.getDateOfBooking(),
                        booking.getTimeFrom(),
                        booking.getTimeTo(),
                        booking.getPurpose(),
                        new UserIDRequest(booking.getUserID())
                ));
            }
        }
    }

    public RoomDTO(int roomID, String roomName, int roomCapacity) {
        this.roomID = roomID;
        this.capacity = roomCapacity;
        this.roomName = roomName;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<BookingDTO> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingDTO> bookings) {
        this.bookings = bookings;
    }
}