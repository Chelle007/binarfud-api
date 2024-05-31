package com.example.binarfud.model.dto.order;

import lombok.Data;

@Data
public class OrderCompleteRequestDto {
    private String destinationAddress;
    private String notes;
}
