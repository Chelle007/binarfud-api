package com.example.binarfud.service;

import com.example.binarfud.model.dto.restaurant.*;
import com.example.binarfud.model.entity.*;
import com.example.binarfud.model.entity.account.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RestaurantService {
    // CREATE
    RestaurantDto create(RestaurantCreateRequestDto restaurantCreateRequestDto);

    // READ
    Restaurant getById(UUID id);
    RestaurantDto getDtoById(UUID id);
    List<RestaurantDto> getList();
    List<RestaurantDto> getOpenList();
    List<RestaurantDto> getListByUser(User user);
    RestaurantReportDto getReport(Restaurant restaurant, LocalDateTime startTime, LocalDateTime endTime);

    // UPDATE
    RestaurantDto update(UUID id, RestaurantUpdateRequestDto restaurantUpdateRequestDto);

    // DELETE
    void safeDeleteById(UUID id);
}
