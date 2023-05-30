package com.group08.onlineShop.dto.requestDTO;

import com.group08.onlineShop.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class StockRequest {
    private Long product;
    private String size;
    private String color;
    private Integer quantity;
}
