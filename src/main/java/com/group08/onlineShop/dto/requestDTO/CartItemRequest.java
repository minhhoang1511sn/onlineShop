package com.group08.onlineShop.dto.requestDTO;

import lombok.Data;

@Data
public class CartItemRequest {
    private Long product;
    private Integer quantity;
    private Double totalPrice;
    private String size;
    private String color;
    private Long account;
}
