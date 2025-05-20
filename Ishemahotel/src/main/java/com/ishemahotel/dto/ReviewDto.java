package com.ishemahotel.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private Long userId;
    private Long hotelId;
    private String comment;
    private int rating;
} 