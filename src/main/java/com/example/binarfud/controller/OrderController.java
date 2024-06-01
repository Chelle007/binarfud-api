package com.example.binarfud.controller;

import com.example.binarfud.model.dto.order.OrderCreateRequestDto;
import com.example.binarfud.model.dto.order.OrderDto;
import com.example.binarfud.model.dto.order.OrderCompleteRequestDto;
import com.example.binarfud.model.dto.order.OrderReceiptDto;
import com.example.binarfud.service.*;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired OrderService orderService;
    @Autowired UserService userService;
    @Autowired RestaurantService restaurantService;
    @Autowired
    ReceiptService receiptService;
    @Autowired JasperService jasperService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Map<String, Object>> addOrder(@RequestBody OrderCreateRequestDto orderCreateRequestDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        data.put("order", orderService.create(orderCreateRequestDto));
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllOrders() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        List<OrderDto> orderList = orderService.getList();
        data.put("orders", orderList);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/history/user/{user_id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Map<String, Object>> getCompletedOrdersByUser(@PathVariable("user_id") UUID userId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        List<OrderDto> orderList = orderService.getCompletedListByUser(userService.getById(userId));
        data.put("orders", orderList);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/history/restaurant/{restaurant_id}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Map<String, Object>> getCompletedOrdersByRestaurant(@PathVariable("restaurant_id") UUID restaurantId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        List<OrderDto> orderList = orderService.getCompletedListByRestaurant(restaurantService.getById(restaurantId));
        data.put("orders", orderList);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable("order_id") UUID orderId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        OrderDto orderDto = orderService.getDtoById(orderId);
        data.put("order", orderDto);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{order_id}/receipt")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Map<String, Object>> getOrderReceipt(@PathVariable("order_id") UUID orderId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        OrderReceiptDto orderReceipt = receiptService.getOrderReceiptDto(orderId);
        data.put("orderReceipt", orderReceipt);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{order_id}/generate/{format}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Resource> generateOrderReceipt(@PathVariable("order_id") UUID orderId, @PathVariable("format") String format) throws JRException {
        byte[] reportContent = jasperService.getOrderReport(receiptService.getOrderReceiptDto(orderId), format);

        ByteArrayResource resource = new ByteArrayResource(reportContent);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("receipt."+format).build().toString())
                .body(resource);
    }

    @PutMapping("/{order_id}/complete_order")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Map<String, Object>> completeOrder(@PathVariable("order_id") UUID orderId, OrderCompleteRequestDto orderCompleteRequestDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        OrderDto updatedOrder = orderService.completeOrder(orderId, orderCompleteRequestDto);
        data.put("order", updatedOrder);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{order_id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable("order_id") UUID orderId) {
        orderService.safeDeleteById(orderId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
