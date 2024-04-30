package com.oop.room_booking.Repository;

import com.oop.room_booking.Model.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, Integer> {
    AppUser findByEmail(String email);
    AppUser findByUserID(int userID); // Changed method name to match property name
}
