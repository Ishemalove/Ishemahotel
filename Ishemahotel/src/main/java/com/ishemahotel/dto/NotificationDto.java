package com.ishemahotel.dto;

import lombok.Data;

@Data
public class NotificationDto {
    private Long id;
    private Long userId;
    private String message;
    private boolean read;
} 