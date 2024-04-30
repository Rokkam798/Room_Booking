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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;

@Controller
public class HistoryController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(@RequestParam int userID) {
        AppUser user = userRepository.findById(userID).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnFormat("User does not exist"));
        }

        LocalDate currentDate = LocalDate.now();

        List<Booking> bookingHistory = bookingRepository.findByUserID(userID);
        List<Map<String, Object>> response = new ArrayList<>();

        for (Booking booking : bookingHistory) {
            // Filter bookings based on date
            if (booking.getDateOfBooking().isBefore(currentDate)) {
                Room room = roomRepository.findById(booking.getRoomID()).orElse(null);
                if (room != null) {
                    Map<String, Object> bookingInfo = createBookingInfo(booking, room);
                    response.add(bookingInfo);
                }
            }
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<?> getUpcomingBooking(@RequestParam("userID") int userID) {
        AppUser user = userRepository.findById(userID).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnFormat("User does not exist"));
        }

        LocalDate currentDate = LocalDate.now();

        List<Booking> bookingHistory = bookingRepository.findByUserID(userID);
        List<Map<String, Object>> response = new ArrayList<>();

        for (Booking booking : bookingHistory) {
            // Filter bookings based on date
            if (booking.getDateOfBooking().isAfter(currentDate)) {
                Room room = roomRepository.findById(booking.getRoomID()).orElse(null);
                if (room != null) {
                    Map<String, Object> bookingInfo = createBookingInfo(booking, room);
                    response.add(bookingInfo);
                }
            }
        }

        return ResponseEntity.ok(response);
    }

    // Helper method to create booking info map
    private Map<String, Object> createBookingInfo(Booking booking, Room room) {
        Map<String, Object> bookingInfo = new HashMap<>();
        bookingInfo.put("room", room.getRoomName());
        bookingInfo.put("roomID", room.getRoomID());
        bookingInfo.put("bookingID", booking.getBookingID());
        bookingInfo.put("dateOfBooking", booking.getDateOfBooking());
        bookingInfo.put("timeFrom", booking.getTimeFrom());
        bookingInfo.put("timeTo", booking.getTimeTo());
        bookingInfo.put("purpose", booking.getPurpose());
        return bookingInfo;
    }

}

