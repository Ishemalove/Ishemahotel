package com.ishemahotel.dto;

import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private String roomNumber;
    private String roomType;
    private Double price;
    private Boolean isAvailable;
    private Long hotelId;
} 