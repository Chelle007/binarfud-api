package com.example.binarfud.service;

import com.example.binarfud.model.dto.order.OrderReceiptDto;
import com.example.binarfud.model.dto.orderDetail.OrderDetailDto;
import com.example.binarfud.model.dto.orderDetail.OrderDetailReportDto;
import com.example.binarfud.model.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    @Autowired ModelMapper modelMapper;
    @Autowired OrderService orderService;
    @Autowired OrderDetailService orderDetailService;
    @Autowired MenuItemService menuItemService;

    @Override
    public OrderReceiptDto getOrderReceiptDto(UUID orderId) {
        Order order = orderService.getById(orderId);
        OrderReceiptDto orderReceiptDto = new OrderReceiptDto();

        orderReceiptDto.setOrderId(orderId);
        orderReceiptDto.setOrderTime(order.getOrderTime());
        orderReceiptDto.setRestaurantName(order.getRestaurant().getName());
        orderReceiptDto.setDestinationAddress(order.getDestinationAddress());

        List<OrderDetailReportDto> orderDetailReportDtoList = new ArrayList<>();

        for (OrderDetailDto orderDetailDto : orderDetailService.getListByOrder(order)) {
            OrderDetailReportDto orderDetailReportDto = modelMapper.map(orderDetailDto, OrderDetailReportDto.class);

            orderDetailReportDto.setMenuItemName(menuItemService.getById(orderDetailDto.getMenuItemId()).getName());

            orderDetailReportDtoList.add(orderDetailReportDto);
        }

        orderReceiptDto.setOrderDetailReportDtoList(orderDetailReportDtoList);
        orderReceiptDto.setTotalPrice(orderService.getTotalPrice(orderId));
        orderReceiptDto.setTotalQty(orderService.getTotalQty(orderId));

        return orderReceiptDto;
    }
}
