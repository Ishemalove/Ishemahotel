package com.ishemahotel.controller;

import com.ishemahotel.dto.BillingDto;
import com.ishemahotel.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billings")
@RequiredArgsConstructor
public class BillingController {
    private final BillingService billingService;

    @PostMapping("/booking/{bookingId}")
    public ResponseEntity<BillingDto> createBilling(@PathVariable Long bookingId) {
        return ResponseEntity.ok(billingService.createBilling(bookingId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillingDto> getBillingById(@PathVariable Long id) {
        return ResponseEntity.ok(billingService.getBillingById(id));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<BillingDto> getBillingByBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(billingService.getBillingByBookingId(bookingId));
    }

    @GetMapping
    public ResponseEntity<List<BillingDto>> getAllBillings() {
        return ResponseEntity.ok(billingService.getAllBillings());
    }
} 