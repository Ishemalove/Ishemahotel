package com.ishemahotel.repository;

import com.ishemahotel.entity.Room;
import com.ishemahotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotel(Hotel hotel);
    List<Room> findByHotelAndIsAvailableTrue(Hotel hotel);
    
    @Query("SELECT r FROM Room r WHERE r.hotel.id = ?1 AND r.isAvailable = true " +
           "AND r.id NOT IN (SELECT b.room.id FROM Booking b WHERE " +
           "b.status = 'CONFIRMED' AND " +
           "((b.checkIn <= ?2 AND b.checkOut >= ?2) OR " +
           "(b.checkIn <= ?3 AND b.checkOut >= ?3) OR " +
           "(b.checkIn >= ?2 AND b.checkOut <= ?3)))")
    List<Room> findAvailableRooms(Long hotelId, LocalDate checkIn, LocalDate checkOut);
} 