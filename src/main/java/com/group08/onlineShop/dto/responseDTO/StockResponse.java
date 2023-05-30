package com.group08.onlineShop.dto.responseDTO;

import com.group08.onlineShop.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class StockResponse {
    private Long id;
    private Long product;
    private String size;
    private String color;
    private Integer quantity;

    public StockResponse(Long id, Long product, String size, String color, Integer quantity) {
        this.id = id;
        this.product = product;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }
}
