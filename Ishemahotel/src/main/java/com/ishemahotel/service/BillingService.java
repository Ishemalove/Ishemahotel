package com.ishemahotel.service;

import com.ishemahotel.entity.Billing;
import com.ishemahotel.entity.Booking;
import com.ishemahotel.repository.BillingRepository;
import com.ishemahotel.repository.BookingRepository;
import com.ishemahotel.dto.BillingDto;
import com.ishemahotel.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BillingService {
    private final BillingRepository billingRepository;
    private final BookingRepository bookingRepository;

    public BillingDto createBilling(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        Billing billing = new Billing();
        billing.setBooking(booking);
        billing.setAmount(booking.getTotalPrice());
        billing.setBillingDate(LocalDateTime.now());
        billing.setIsPaid(false);

        return convertToDto(billingRepository.save(billing));
    }

    public BillingDto getBillingById(Long id) {
        return billingRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Billing not found with id: " + id));
    }

    public BillingDto getBillingByBookingId(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        
        return billingRepository.findByBooking(booking)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Billing not found for booking id: " + bookingId));
    }

    public List<BillingDto> getAllBillings() {
        return billingRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private BillingDto convertToDto(Billing billing) {
        BillingDto dto = new BillingDto();
        dto.setId(billing.getId());
        dto.setBookingId(billing.getBooking().getId());
        dto.setAmount(billing.getAmount());
        dto.setBillingDate(billing.getBillingDate());
        dto.setIsPaid(billing.getIsPaid());
        return dto;
    }
} 