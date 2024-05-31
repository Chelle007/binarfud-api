package com.example.binarfud.model.dto.orderDetail;

import lombok.Data;

@Data
public class OrderDetailReportDto {
    String menuItemName;
    String size;
    int qty;
    int price;
}
