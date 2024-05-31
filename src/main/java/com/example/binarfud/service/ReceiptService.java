package com.example.binarfud.service;

import com.example.binarfud.model.dto.order.OrderReceiptDto;

import java.util.UUID;

public interface ReceiptService {
    OrderReceiptDto getOrderReceiptDto(UUID orderId);
}
