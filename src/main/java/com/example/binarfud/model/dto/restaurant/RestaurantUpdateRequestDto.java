package com.example.binarfud.model.dto.restaurant;

import lombok.Data;

import java.util.UUID;

@Data
public class RestaurantUpdateRequestDto {
    private String name;
    private String location;
    private boolean open;
    private UUID ownerId;
}
