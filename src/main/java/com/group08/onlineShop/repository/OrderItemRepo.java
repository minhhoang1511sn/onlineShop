package com.group08.onlineShop.repository;

import com.group08.onlineShop.model.Order;
import com.group08.onlineShop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findOrderItemByOrder(Order order);
}
