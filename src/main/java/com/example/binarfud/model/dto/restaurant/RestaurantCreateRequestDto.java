package com.example.binarfud.model.dto.restaurant;

import lombok.Data;

import java.util.UUID;

@Data
public class RestaurantCreateRequestDto {
    private String name;
    private String location;
    private UUID ownerId;
}
