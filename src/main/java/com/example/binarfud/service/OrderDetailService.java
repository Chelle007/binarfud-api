package com.example.binarfud.service;

import com.example.binarfud.model.dto.orderDetail.OrderDetailCreateRequestDto;
import com.example.binarfud.model.dto.orderDetail.OrderDetailDto;
import com.example.binarfud.model.dto.orderDetail.OrderDetailUpdateRequestDto;
import com.example.binarfud.model.entity.*;

import java.util.List;
import java.util.UUID;

public interface OrderDetailService {
    // CREATE
    OrderDetailDto create(OrderDetailCreateRequestDto orderDetailCreateRequestDto);

    // READ
    OrderDetail getById(UUID id);
    OrderDetailDto getDtoById(UUID id);
    List<OrderDetailDto> getListByOrder(Order order);
    List<OrderDetailDto> getListByMenuItem(MenuItem menuItem);

    // UPDATE
    OrderDetailDto update(UUID id, OrderDetailUpdateRequestDto orderDetailUpdateRequestDto);

    // DELETE
    void safeDeleteById(UUID id);
}
