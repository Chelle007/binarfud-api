package com.example.binarfud.service;

import com.example.binarfud.model.dto.menuItem.MenuItemCreateRequestDto;
import com.example.binarfud.model.dto.menuItem.MenuItemDto;
import com.example.binarfud.model.dto.menuItem.MenuItemUpdateRequestDto;
import com.example.binarfud.model.entity.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MenuItemService {
    // CREATE
    MenuItemDto create(MenuItemCreateRequestDto menuItemCreateRequestDto);

    // READ
    MenuItem getById(UUID id);
    MenuItemDto getDtoById(UUID id);
    List<MenuItemDto> getListByRestaurant(Restaurant restaurant);
    List<MenuItemDto> getListByRestaurantAndPage(Restaurant restaurant, int page, int menuItemPerPage);
    List<String> getAvailableSize(MenuItem menuItem);
    int getTotalPage(Restaurant restaurant, int menuItemCountPerPage);
    int getTotalPrice(UUID id, LocalDateTime startTime, LocalDateTime endTime);
    int getTotalQty(UUID id, LocalDateTime startTime, LocalDateTime endTime);

    // UPDATE
    MenuItemDto update(UUID id, MenuItemUpdateRequestDto menuItemUpdateRequestDto);

    // DELETE
    void safeDeleteById(UUID id);
}
