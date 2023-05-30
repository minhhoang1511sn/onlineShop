package com.group08.onlineShop.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {
    private Long product;
    private Integer quantity;
    private Double totalPrice;
    private String size;
    private String color;
    private Long order;
}
