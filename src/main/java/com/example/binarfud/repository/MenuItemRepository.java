package com.example.binarfud.repository;

import com.example.binarfud.model.entity.MenuItem;
import com.example.binarfud.model.entity.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {

    @Query(value = "SELECT SUM(od.price) " +
            "FROM order_detail od " +
            "WHERE od.menu_item_id = :menuItemId " +
            "AND od.deleted = false",
            nativeQuery = true)
    Integer getTotalPrice(@Param("menuItemId") UUID menuItemId);

    @Query(value = "SELECT SUM(od.price) " +
            "FROM order_detail od " +
            "JOIN orders o ON o.id = od.order_id " +
            "WHERE od.menu_item_id = :menuItemId " +
            "AND od.deleted = false " +
            "AND o.order_time > :startTime " +
            "AND o.order_time < :endTime",
            nativeQuery = true)
    Integer getTotalPrice(@Param("menuItemId") UUID menuItemId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query(value = "SELECT SUM(od.qty) " +
            "FROM order_detail od " +
            "WHERE od.menu_item_id = :menuItemId " +
            "AND od.deleted = false",
            nativeQuery = true)
    Integer getTotalQty(@Param("menuItemId") UUID menuItemId);

    @Query(value = "SELECT SUM(od.qty) " +
            "FROM order_detail od " +
            "JOIN orders o ON o.id = od.order_id " +
            "WHERE od.menu_item_id = :menuItemId " +
            "AND od.deleted = false " +
            "AND o.order_time > :startTime " +
            "AND o.order_time < :endTime",
            nativeQuery = true)
    Integer getTotalQty(@Param("menuItemId") UUID menuItemId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    boolean existsByRestaurantAndName(Restaurant restaurant, String name);
    boolean existsByName(String name);
    List<MenuItem> findByRestaurant(Restaurant restaurant);
    List<MenuItem> findByRestaurant(Restaurant restaurant, Pageable pageable);
}
