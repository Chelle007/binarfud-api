package com.example.binarfud.model.dto.restaurant;

import com.example.binarfud.model.dto.menuItem.MenuItemIncomeDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class RestaurantReportDto {
    private UUID restaurantId;
    private int totalMenuItemQty;
    private int totalIncome;

    private List<MenuItemIncomeDto> totalMenuItemIncome;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
