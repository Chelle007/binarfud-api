package com.example.binarfud.repository;

import com.example.binarfud.model.entity.Restaurant;
import com.example.binarfud.model.entity.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    List<Restaurant> findByOpen(boolean b);
    List<Restaurant> findByOwner(User user);
    boolean existsByName(String name);
}
