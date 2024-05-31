package com.example.binarfud.model.dto.order;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID id;

    private LocalDateTime orderTime;
    private String destinationAddress;
    private String notes;
    private boolean completed;
    private UUID userId;
    private UUID restaurantId;
}
