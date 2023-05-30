package com.group08.onlineShop.repository;

import com.group08.onlineShop.model.PayPalOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayPalOrderRepo extends JpaRepository<PayPalOrder, Long> {
//    PayPalOrder findByPaypalOrderId(String paypalOrderId);
    Optional<PayPalOrder> findPayPalOrderByPaypalOrderID(String paypal_order_id);
}
