package com.ishemahotel.dto;

import lombok.Data;

@Data
public class HotelDto {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String country;
    private String description;
    private Double rating;
} 