package com.ishemahotel.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BillingDto {
    private Long id;
    private Long bookingId;
    private Double amount;
    private LocalDateTime billingDate;
    private Boolean isPaid;
} 