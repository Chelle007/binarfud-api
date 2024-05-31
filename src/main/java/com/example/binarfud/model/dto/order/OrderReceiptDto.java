package com.example.binarfud.model.dto.order;

import com.example.binarfud.model.dto.orderDetail.OrderDetailReportDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderReceiptDto {
    UUID orderId;
    LocalDateTime orderTime;
    String restaurantName;
    String destinationAddress;
    List<OrderDetailReportDto> orderDetailReportDtoList;
    int totalQty;
    int totalPrice;
}
