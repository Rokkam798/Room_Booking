package com.oop.room_booking.Controller;

import com.oop.room_booking.DTO.ReturnFormat;
import com.oop.room_booking.Model.AppUser;
import com.oop.room_booking.DTO.UserDTO;
import com.oop.room_booking.Request.UserDetailsResponse;
import com.oop.room_booking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/user")
    public ResponseEntity<?> getUserDetail(@RequestParam("userID") int userID) {
        AppUser bookinguser = userRepository.findById(userID).orElse(null);
        if (bookinguser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnFormat("User does not exist"));
        } else {
            UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
            userDetailsResponse.setName(bookinguser.getName());
            userDetailsResponse.setUserID(bookinguser.getUserID());
            userDetailsResponse.setEmail(bookinguser.getEmail());
            return ResponseEntity.ok(userDetailsResponse);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<AppUser> users = (List<AppUser>) userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            List<UserDTO> userDTOs = new ArrayList<>();
            for (AppUser user : users) {
                UserDTO userDTO = new UserDTO(user.getUserID(), user.getEmail(), user.getName());
                userDTOs.add(userDTO);
            }
            return ResponseEntity.ok(userDTOs);
        }
    }
}
