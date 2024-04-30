package com.oop.room_booking.Repository;

import com.oop.room_booking.Model.Booking;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Integer> {
    List<Booking> findByUserID(int userID);
    Booking findByUserIDAndDateOfBookingAfter(int userID, Date date);
}
