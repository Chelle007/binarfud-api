package com.example.binarfud.model.dto.menuItem;

import com.example.binarfud.model.entity.MenuItem;
import lombok.Data;

import java.util.UUID;

@Data
public class MenuItemCreateRequestDto {
    private String name;
    private MenuItem.Type type;
    private Integer priceS;
    private Integer priceM;
    private Integer priceL;
    private UUID restaurantId;
}
