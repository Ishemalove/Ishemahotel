package com.ishemahotel.repository;

import com.ishemahotel.entity.Booking;
import com.ishemahotel.entity.User;
import com.ishemahotel.entity.Hotel;
import com.ishemahotel.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
    List<Booking> findByRoom_Hotel(Hotel hotel);
    List<Booking> findByStatus(BookingStatus status);
} 