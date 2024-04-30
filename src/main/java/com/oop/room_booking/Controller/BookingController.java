package com.oop.room_booking.Controller;
import com.oop.room_booking.DTO.ReturnFormat;
import com.oop.room_booking.Model.AppUser;
import com.oop.room_booking.Model.Booking;
import com.oop.room_booking.Model.Room;
import com.oop.room_booking.Repository.BookingRepository;
import com.oop.room_booking.Repository.RoomRepository;
import com.oop.room_booking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/book")
    public ResponseEntity<?> Booking(@RequestBody Booking newBooking) {
        AppUser user = userRepository.findById(newBooking.getUserID()).orElse(null);
        Room room = roomRepository.findById(newBooking.getRoomID()).orElse(null);

        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnFormat("Room does not exist"));
        }
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnFormat("User does not exist"));
        }
        if (!isValidTime(newBooking.getTimeFrom()) || !isValidTime(newBooking.getTimeTo())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ReturnFormat("Invalid date/time"));
        }

        // Check if the end time is after the start time
        if (newBooking.getTimeFrom().compareTo(newBooking.getTimeTo()) >= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ReturnFormat("Invalid date/time"));
        }
        List<Booking> existingBookings = room.getBookings();
        for (Booking existingBooking : existingBookings) {
            if(newBooking.getDateOfBooking().equals(existingBooking.getDateOfBooking())){
                if (newBooking.getTimeFrom().compareTo(existingBooking.getTimeFrom()) >= 0 &&
                        newBooking.getTimeFrom().compareTo(existingBooking.getTimeTo()) < 0) {
                    // New booking starts within the existing booking
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ReturnFormat("Room unavailable"));
                }
                if (newBooking.getTimeTo().compareTo(existingBooking.getTimeFrom()) > 0 &&
                        newBooking.getTimeTo().compareTo(existingBooking.getTimeTo()) <= 0) {
                    // New booking ends within the existing booking
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ReturnFormat("Room unavailable"));
                }
                if (newBooking.getTimeFrom().compareTo(existingBooking.getTimeFrom()) <= 0 &&
                        newBooking.getTimeTo().compareTo(existingBooking.getTimeTo()) >= 0) {
                    // New booking completely encloses the existing booking
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ReturnFormat("Room unavailable"));
                }
            }
        }

        // Create a new Booking entity
        Booking booking = new Booking();
        booking.setUserID(newBooking.getUserID());
        booking.setRoomID(newBooking.getRoomID());
        booking.setDateOfBooking(newBooking.getDateOfBooking());
        booking.setTimeFrom(newBooking.getTimeFrom());
        booking.setTimeTo(newBooking.getTimeTo());
        booking.setPurpose(newBooking.getPurpose());

        existingBookings.add(booking);
        room.setBookings(existingBookings);
        booking.setRoom(room);
        roomRepository.save(room);
        return ResponseEntity.ok("Booking created successfully");
    }

    @PatchMapping("/book")
    public ResponseEntity<?> editBooking(@RequestBody Booking newBooking) {
        Booking existingBooking = bookingRepository.findById(newBooking.getBookingID()).orElse(null);

        if (existingBooking == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnFormat("Booking does not exist"));
        }

        AppUser user = userRepository.findById(newBooking.getUserID()).orElse(null);
        Room room = roomRepository.findById(newBooking.getRoomID()).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnFormat("User does not exist"));
        }
        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnFormat("Room does not exist"));
        }

        List<Booking> existingBookings = room.getBookings();
        for (Booking existingBookingItem : existingBookings) {
            if (existingBookingItem.getBookingID() != newBooking.getBookingID() &&
                    isOverlappingBooking(newBooking, existingBookingItem)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ReturnFormat("Room unavailable"));
            }
        }

        existingBooking.setDateOfBooking(newBooking.getDateOfBooking());
        existingBooking.setTimeFrom(newBooking.getTimeFrom());
        existingBooking.setTimeTo(newBooking.getTimeTo());
        existingBooking.setPurpose(newBooking.getPurpose());

        bookingRepository.save(existingBooking);
        return ResponseEntity.ok("Booking updated successfully");
    }
    @DeleteMapping("/book")
    public ResponseEntity<?> deleteBooking(@RequestParam("bookingID") int bookingID) {
        Booking booking = bookingRepository.findById(bookingID).orElse(null);
        if (booking == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnFormat("Booking does not exist"));
        }

        Room room = roomRepository.findById(booking.getRoomID()).orElse(null);
        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnFormat("Room does not exist"));
        }

        room.removeBooking(booking);
        roomRepository.save(room);
        bookingRepository.delete(booking);

        return ResponseEntity.ok("Booking deleted successfully");
    }

    private boolean isOverlappingBooking(Booking newBooking, Booking existingBooking) {
        return (newBooking.getTimeFrom().compareTo(existingBooking.getTimeFrom()) >= 0 &&
                newBooking.getTimeFrom().compareTo(existingBooking.getTimeTo()) < 0) ||
                (newBooking.getTimeTo().compareTo(existingBooking.getTimeFrom()) > 0 &&
                        newBooking.getTimeTo().compareTo(existingBooking.getTimeTo()) <= 0) ||
                (newBooking.getTimeFrom().compareTo(existingBooking.getTimeFrom()) <= 0 &&
                        newBooking.getTimeTo().compareTo(existingBooking.getTimeTo()) >= 0);
    }
    private boolean isValidTime(String time) {
        return time.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");
    }

}
