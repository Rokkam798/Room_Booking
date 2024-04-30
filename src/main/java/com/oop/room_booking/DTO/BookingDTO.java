package com.oop.room_booking.DTO;
import com.oop.room_booking.Request.UserIDRequest;

import java.time.LocalDate;
import java.util.Date;

public class BookingDTO {
    private int bookingID;
    private LocalDate dateOfBooking;
    private String timeFrom;
    private String timeTo;
    private String purpose;
    private UserIDRequest user;

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public LocalDate getDateOfBooking() {
        return dateOfBooking;
    }

    public void setDateOfBooking(LocalDate dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public UserIDRequest getUser() {
        return user;
    }

    public void setUser(UserIDRequest user) {
        this.user = user;
    }

    public BookingDTO(int bookingID, LocalDate dateOfBooking, String timeFrom, String timeTo, String purpose, UserIDRequest user) {
        this.bookingID = bookingID;
        this.dateOfBooking = dateOfBooking;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.purpose = purpose;
        this.user = user;
    }
}
