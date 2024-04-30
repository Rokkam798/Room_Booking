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
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody SignupRequest signupRequest) {
        if (signupRequest.getEmail() == null || signupRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ReturnFormat("Invalid request body"));
        }

        AppUser existingUser = userRepository.findByEmail(signupRequest.getEmail());
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ReturnFormat("User does not exist"));
        } else {
            String userPassword = existingUser.getPassword();
            String requestPassword = signupRequest.getPassword();
            if (userPassword == null || requestPassword == null || !userPassword.equals(requestPassword)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ReturnFormat("Username/Password Incorrect"));
            } else {
                return ResponseEntity.ok("Login Successful");
            }
        }
    }
}
