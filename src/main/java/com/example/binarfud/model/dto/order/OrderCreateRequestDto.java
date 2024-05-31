package com.example.binarfud.model.dto.order;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderCreateRequestDto {
    private UUID userId;
    private UUID restaurantId;
}
