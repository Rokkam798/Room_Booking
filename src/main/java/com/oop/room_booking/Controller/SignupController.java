package com.oop.room_booking.Controller;

import com.oop.room_booking.DTO.ReturnFormat;
import com.oop.room_booking.Model.AppUser;
import com.oop.room_booking.Repository.UserRepository;
import com.oop.room_booking.Request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SignupController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        AppUser existingUser = userRepository.findByEmail(signupRequest.getEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnFormat("Forbidden, Account already exists"));
        }
        AppUser bookinguser = new AppUser();
        bookinguser.setEmail(signupRequest.getEmail());
        bookinguser.setName(signupRequest.getName());
        bookinguser.setPassword(signupRequest.getPassword());
        userRepository.save(bookinguser);
        return ResponseEntity.ok("Account Creation Successful");
    }
}
