package com.example.binarfud.model.dto.restaurant;

import lombok.Data;

import java.util.UUID;

@Data
public class RestaurantDto {
    private UUID id;

    private String name;
    private String location;
    private boolean open;
    private UUID ownerId;
}
