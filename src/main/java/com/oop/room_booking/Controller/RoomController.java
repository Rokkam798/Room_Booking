package com.oop.room_booking.Controller;
import com.oop.room_booking.DTO.BookingDTO;
import com.oop.room_booking.DTO.ReturnFormat;
import com.oop.room_booking.DTO.RoomDTO;
import com.oop.room_booking.DTO.UserDTO;
import com.oop.room_booking.Model.AppUser;
import com.oop.room_booking.Model.Booking;
import com.oop.room_booking.Model.Room;
import com.oop.room_booking.Repository.RoomRepository;
import com.oop.room_booking.Repository.UserRepository;
import com.oop.room_booking.Request.UserIDRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/rooms")
    public ResponseEntity<?> addRoom(@RequestBody Room newroom) {
        Room existingRoom = roomRepository.findByRoomName(newroom.getRoomName());
        if (existingRoom != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ReturnFormat("Room already exists"));
        }
        if(newroom.getRoomCapacity()<0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ReturnFormat("Invalid capacity"));
        }
        Room room = new Room();
        room.setRoomName(newroom.getRoomName());
        room.setRoomCapacity(newroom.getRoomCapacity());
        roomRepository.save(newroom);
        return ResponseEntity.ok("Room Creation Successful");
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDTO>> getAllRooms(@RequestParam(required = false) Integer capacity) {
        List<Room> rooms;
        if (capacity != null) {
            rooms = roomRepository.findByRoomCapacity(capacity); // Assuming there's a method findByRoomCapacity in your repository
        } else {
            rooms = (List<Room>) roomRepository.findAll();
        }

        if (rooms.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<RoomDTO> roomDTOs = new ArrayList<>();
            for (Room room : rooms) {
                RoomDTO roomDTO;
                List<Booking> bookings = room.getBookings();
                if (bookings == null || bookings.isEmpty()) {
                    // If the room has no bookings, create a RoomDTO with only roomID and capacity
                    roomDTO = new RoomDTO(room.getRoomID(), room.getRoomName(), room.getRoomCapacity());
                } else {
                    List<BookingDTO> bookingDTOs = new ArrayList<>();
                    for (Booking booking : bookings) {
                        // Construct the BookingDTO for each booking
                        BookingDTO bookingDTO = new BookingDTO(
                                booking.getBookingID(),
                                booking.getDateOfBooking(),
                                booking.getTimeFrom(),
                                booking.getTimeTo(),
                                booking.getPurpose(),
                                new UserIDRequest(booking.getUserID())
                        );
                        bookingDTOs.add(bookingDTO);
                    }
                    // Construct the RoomDTO with multiple bookings
                    roomDTO = new RoomDTO(room);
                }
                roomDTOs.add(roomDTO);
            }
            return ResponseEntity.ok(roomDTOs);
        }
    }






    @PatchMapping("/rooms")
    public ResponseEntity<?> updateRoom(@RequestBody Room newroom) {
        Room existingRoom = roomRepository.findByRoomName(newroom.getRoomName());
        if (existingRoom == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnFormat("Room does not exist"));
        }

        Room roomWithSameName = roomRepository.findByRoomName(newroom.getRoomName());
        if (roomWithSameName != null && roomWithSameName.getRoomID() != newroom.getRoomID()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ReturnFormat("Room with given name already exists"));
        }

        if(newroom.getRoomCapacity()<0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ReturnFormat("Invalid capacity"));
        }
        existingRoom.setRoomName(newroom.getRoomName());
        existingRoom.setRoomCapacity(newroom.getRoomCapacity());
        roomRepository.save(existingRoom);
        return ResponseEntity.ok("Room edited successfully");
    }

    @DeleteMapping("/rooms")
    public ResponseEntity<?> deleteRoom(@RequestBody Room newroom) {
        Room existingRoom = roomRepository.findById(newroom.getRoomID()).orElse(null);
        if (existingRoom == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnFormat("Room does not exist"));
        }
        roomRepository.delete(existingRoom);
        return ResponseEntity.ok("Room deleted successfully");
    }

}
