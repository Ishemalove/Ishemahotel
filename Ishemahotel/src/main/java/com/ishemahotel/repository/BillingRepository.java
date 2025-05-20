package com.ishemahotel.repository;

import com.ishemahotel.entity.Billing;
import com.ishemahotel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    Optional<Billing> findByBooking(Booking booking);
} 