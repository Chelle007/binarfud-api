package com.example.binarfud.controller;

import com.example.binarfud.model.dto.menuItem.MenuItemCreateRequestDto;
import com.example.binarfud.model.dto.menuItem.MenuItemDto;
import com.example.binarfud.model.dto.menuItem.MenuItemUpdateRequestDto;
import com.example.binarfud.service.MenuItemService;
import com.example.binarfud.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("menu_item")
public class MenuItemController {
    @Autowired MenuItemService menuItemService;
    @Autowired RestaurantService restaurantService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Map<String, Object>> addMenuItem(@RequestBody MenuItemCreateRequestDto menuItemCreateRequestDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        data.put("menuItem", menuItemService.create(menuItemCreateRequestDto));
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{restaurant_id}")
    public ResponseEntity<Map<String, Object>> getAllMenuItemsByRestaurant(@PathVariable("restaurant_id") UUID restaurantId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        List<MenuItemDto> menuItemList = menuItemService.getListByRestaurant(restaurantService.getById(restaurantId));
        data.put("menuItems", menuItemList);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurant_id}/page/{page}")
    public ResponseEntity<Map<String, Object>> getAllMenuItemsByRestaurantAndPage(@PathVariable("restaurant_id") UUID restaurantId, @PathVariable("page") int page, @RequestParam int menuItemCountPerPage) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        List<MenuItemDto> menuItemList = menuItemService.getListByRestaurantAndPage(restaurantService.getById(restaurantId), page, menuItemCountPerPage);
        data.put("menuItems", menuItemList);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurant_id}/total_pages")
    public ResponseEntity<Map<String, Object>> getTotalPage(@PathVariable("restaurant_id") UUID restaurantId, @RequestParam int menuItemCountPerPage) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        int totalPage = menuItemService.getTotalPage(restaurantService.getById(restaurantId), menuItemCountPerPage);
        data.put("totalPages", totalPage);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{menu_item_id}")
    public ResponseEntity<Map<String, Object>> getMenuItemById(@PathVariable("menu_item_id") UUID menuItemId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        MenuItemDto menuItem = menuItemService.getDtoById(menuItemId);
        data.put("menuItem", menuItem);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{menu_item_id}/available_sizes")
    public ResponseEntity<Map<String, Object>> getAvailableSizes(@PathVariable("menu_item_id") UUID menuItemId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        List<String> availableSizes = menuItemService.getAvailableSize(menuItemService.getById(menuItemId));
        data.put("availableSizes", availableSizes);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{menu_item_id}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Map<String, Object>> updateMenuItem(@PathVariable("menu_item_id") UUID menuItemId, MenuItemUpdateRequestDto menuItemUpdateRequestDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        MenuItemDto updatedMenuItem = menuItemService.update(menuItemId, menuItemUpdateRequestDto);
        data.put("menuItem", updatedMenuItem);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{menu_item_id}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Map<String, Object>> deleteMenuItem(@PathVariable("menu_item_id") UUID menuItemId) {
        menuItemService.safeDeleteById(menuItemId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
