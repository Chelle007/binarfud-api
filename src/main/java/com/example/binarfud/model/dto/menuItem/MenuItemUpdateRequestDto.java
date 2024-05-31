package com.example.binarfud.model.dto.menuItem;

import com.example.binarfud.model.entity.MenuItem;
import lombok.Data;

@Data
public class MenuItemUpdateRequestDto {
    private String name;
    private MenuItem.Type type;
    private Integer priceS;
    private Integer priceM;
    private Integer priceL;
}
