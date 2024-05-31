package com.example.binarfud.model.dto.menuItem;

import com.example.binarfud.model.entity.MenuItem;
import lombok.Data;

import java.util.UUID;

@Data
public class MenuItemDto {
    private UUID id;

    private String name;
    private MenuItem.Type type;
    private Integer priceS;
    private Integer priceM;
    private Integer priceL;
    private UUID restaurantId;
}
