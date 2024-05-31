package com.example.binarfud.service;

import com.example.binarfud.exception.MenuItemNameExistedException;
import com.example.binarfud.exception.MenuItemNotFoundException;
import com.example.binarfud.model.dto.menuItem.MenuItemCreateRequestDto;
import com.example.binarfud.model.dto.menuItem.MenuItemDto;
import com.example.binarfud.model.dto.menuItem.MenuItemUpdateRequestDto;
import com.example.binarfud.model.entity.*;
import com.example.binarfud.model.entity.MenuItem;
import com.example.binarfud.repository.MenuItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {
    @Autowired RestaurantService restaurantService;
    @Autowired MenuItemRepository menuItemRepository;
    @Autowired ModelMapper modelMapper;

    @Override
    public MenuItemDto create(MenuItemCreateRequestDto menuItemCreateRequestDto) {
        MenuItem menuItem = modelMapper.map(menuItemCreateRequestDto, MenuItem.class);

        menuItem.setRestaurant(restaurantService.getById(menuItemCreateRequestDto.getRestaurantId()));

        if (menuItemRepository.existsByRestaurantAndName(menuItem.getRestaurant(), menuItem.getName())) {
            log.error("Name existed: {}", menuItem.getName());
            throw new MenuItemNameExistedException("Name existed: " + menuItem.getName());
        }

        menuItem = menuItemRepository.save(menuItem);
        log.info("Menu item {} successfully added", menuItem.getName());

        return modelMapper.map(menuItem, MenuItemDto.class);
    }

    @Override
    public MenuItem getById(UUID id) {
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(id);
        if (optionalMenuItem.isPresent()) {
            return optionalMenuItem.get();
        }

        log.error("Menu item not found: {}", (id));
        throw new MenuItemNotFoundException("Menu item not found: " + id);
    }

    @Override
    public MenuItemDto getDtoById(UUID id) {
        return modelMapper.map(getById(id), MenuItemDto.class);
    }

    @Override
    public List<MenuItemDto> getListByRestaurant(Restaurant restaurant) {
        return menuItemRepository.findByRestaurant(restaurant).stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemDto.class))
                .toList();
    }

    @Override
    public List<MenuItemDto> getListByRestaurantAndPage(Restaurant restaurant, int page, int menuItemCountPerPage) {
        Pageable pageable = PageRequest.of(page, menuItemCountPerPage);
        return menuItemRepository.findByRestaurant(restaurant, pageable).stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemDto.class))
                .toList();
    }

    @Override
    public List<String> getAvailableSize(MenuItem menuItem) {
        ArrayList<String> availableSize = new ArrayList<>();

        availableSize.add("M");
        if (menuItem.getPriceS()!=null) availableSize.add("S");
        if (menuItem.getPriceL()!=null) availableSize.add("L");

        return availableSize;
    }

    @Override
    public int getTotalPage(Restaurant restaurant, int menuItemCountPerPage) {
        return (int) Math.ceil((double) getListByRestaurant(restaurant).size() / menuItemCountPerPage);
    }

    @Override
    public int getTotalPrice(UUID id, LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null) {
            startTime = LocalDateTime.of(2000, 1, 1, 0, 0);
        }

        if (endTime == null) {
            endTime = LocalDateTime.now();
        }

        log.info("Start: {}", startTime);
        log.info("End: {}", endTime);

        return Optional.ofNullable(menuItemRepository.getTotalPrice(id, startTime, endTime)).orElse(0);
    }

    @Override
    public int getTotalQty(UUID id, LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null) {
            startTime = LocalDateTime.of(2000, 1, 1, 0, 0);
        }

        if (endTime == null) {
            endTime = LocalDateTime.now();
        }

        return Optional.ofNullable(menuItemRepository.getTotalQty(id, startTime, endTime)).orElse(0);
    }

    @Override
    public MenuItemDto update(UUID id, MenuItemUpdateRequestDto menuItemUpdateRequestDto) {
        MenuItem existingMenuItem = getById(id);

        existingMenuItem.setName(menuItemUpdateRequestDto.getName());
        existingMenuItem.setType(menuItemUpdateRequestDto.getType());
        existingMenuItem.setPriceS(menuItemUpdateRequestDto.getPriceS());
        existingMenuItem.setPriceM(menuItemUpdateRequestDto.getPriceM());
        existingMenuItem.setPriceL(menuItemUpdateRequestDto.getPriceL());

        MenuItem updatedMenuItem = menuItemRepository.save(existingMenuItem);
        return modelMapper.map(updatedMenuItem, MenuItemDto.class);
    }

    @Override
    public void safeDeleteById(UUID id) {
        MenuItem menuItem = getById(id);
        menuItem.setDeleted(true);
        menuItemRepository.save(menuItem);
        log.info("Menu item {} has been deleted", id);
    }
}
