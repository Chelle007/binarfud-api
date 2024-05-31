package com.example.binarfud.controller;

import com.example.binarfud.model.dto.restaurant.*;
import com.example.binarfud.service.RestaurantService;
import com.example.binarfud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("restaurant")
public class RestaurantController {
    @Autowired RestaurantService restaurantService;
    @Autowired UserService userService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> addRestaurant(@RequestBody RestaurantCreateRequestDto restaurantCreateRequestDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        data.put("restaurant", restaurantService.create(restaurantCreateRequestDto));
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllRestaurants() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        List<RestaurantDto> restaurantList = restaurantService.getList();
        data.put("restaurants", restaurantList);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/open_restaurants")
    public ResponseEntity<Map<String, Object>> getAllOpenRestaurants() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        List<RestaurantDto> openRestaurantList = restaurantService.getOpenList();
        data.put("openRestaurants", openRestaurantList);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<Map<String, Object>> getRestaurantsByUser(@PathVariable("user_id") UUID userId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        List<RestaurantDto> usersRestaurantList = restaurantService.getListByUser(userService.getById(userId));
        data.put("usersRestaurants", usersRestaurantList);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{restaurant_id}")
    public ResponseEntity<Map<String, Object>> getRestaurantById(@PathVariable("restaurant_id") UUID restaurantId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        RestaurantDto restaurant = restaurantService.getDtoById(restaurantId);
        data.put("restaurant", restaurant);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{restaurant_id}/report")
    public ResponseEntity<Map<String, Object>> generateRestaurantReport(@PathVariable("restaurant_id") UUID restaurantId, @RequestParam(required = false) LocalDateTime startTime, @RequestParam(required = false) LocalDateTime endTime) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        RestaurantReportDto restaurantReport = restaurantService.getReport(restaurantService.getById(restaurantId), startTime, endTime);
        data.put("restaurantReport", restaurantReport);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{restaurant_id}")
    public ResponseEntity<Map<String, Object>> updateRestaurant(@PathVariable("restaurant_id") UUID restaurantId, RestaurantUpdateRequestDto restaurantUpdateRequestDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        RestaurantDto updatedRestaurant = restaurantService.update(restaurantId, restaurantUpdateRequestDto);
        data.put("restaurant", updatedRestaurant);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{restaurant_id}")
    public ResponseEntity<Map<String, Object>> deleteRestaurant(@PathVariable("restaurant_id") UUID restaurantId) {
        restaurantService.safeDeleteById(restaurantId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
