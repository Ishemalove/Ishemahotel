package com.ishemahotel.dto;

import com.ishemahotel.entity.BookingStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingDto {
    private Long id;
    private Long userId;
    private Long roomId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private BookingStatus status;
    private Double totalPrice;
} 