package com.example.binarfud.service;

import com.example.binarfud.model.dto.order.OrderCompleteRequestDto;
import com.example.binarfud.model.dto.order.OrderCreateRequestDto;
import com.example.binarfud.model.dto.order.OrderDto;
import com.example.binarfud.model.entity.*;
import com.example.binarfud.model.entity.account.User;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    // CREATE
    OrderDto create(OrderCreateRequestDto orderCreateRequestDto);

    // READ
    Order getById(UUID id);
    OrderDto getDtoById(UUID id);
    List<OrderDto> getList();
    List<OrderDto> getCompletedListByUser(User user);
    List<OrderDto> getCompletedListByRestaurant(Restaurant restaurant);
    int getTotalPrice(UUID id);
    int getTotalQty(UUID id);

    // UPDATE
    OrderDto completeOrder(UUID id, OrderCompleteRequestDto orderCompleteRequestDto);

    // DELETE
    void safeDeleteById(UUID id);
}
