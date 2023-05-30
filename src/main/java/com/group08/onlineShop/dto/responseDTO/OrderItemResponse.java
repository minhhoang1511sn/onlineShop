package com.group08.onlineShop.dto.responseDTO;

import com.group08.onlineShop.model.Order;
import com.group08.onlineShop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    private Long id;
    private Product product;
    private Integer quantity;
    private Double totalPrice;
    private String size;
    private String color;
    private Order order;
}
