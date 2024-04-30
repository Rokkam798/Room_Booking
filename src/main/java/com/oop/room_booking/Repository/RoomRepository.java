package com.oop.room_booking.Repository;

import com.oop.room_booking.Model.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    Room findByRoomName(String roomName);
    List<Room> findByRoomCapacity(Integer capacity);
}