package com.example.binarfud.repository;

import com.example.binarfud.model.entity.Order;
import com.example.binarfud.model.entity.Restaurant;
import com.example.binarfud.model.entity.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query(value = "SELECT SUM(od.price) FROM order_detail od WHERE od.order_id = :orderId AND od.deleted = false", nativeQuery = true)
    Integer getTotalPrice(@Param("orderId") UUID orderID);

    @Query(value = "SELECT SUM(od.qty) FROM order_detail od WHERE od.order_id = :orderId AND od.deleted = false", nativeQuery = true)
    Integer getTotalQty(@Param("orderId") UUID orderID);

    List<Order> findByUserAndCompleted(User user, boolean completed);
    List<Order> findByRestaurantAndCompleted(Restaurant restaurant, boolean completed);
}
