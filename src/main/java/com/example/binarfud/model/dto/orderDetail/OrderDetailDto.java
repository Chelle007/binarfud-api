package com.example.binarfud.model.dto.orderDetail;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailDto {
    private UUID id;

    private String size;
    private int qty;
    private int price;
    private UUID menuItemId;
    private UUID orderId;
}
