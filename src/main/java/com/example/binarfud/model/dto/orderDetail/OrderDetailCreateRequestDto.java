package com.example.binarfud.model.dto.orderDetail;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailCreateRequestDto {
    private String size;
    private int qty;
    private UUID orderId;
    private UUID menuItemId;
}
