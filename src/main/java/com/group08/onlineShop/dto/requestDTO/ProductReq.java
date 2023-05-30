package com.group08.onlineShop.dto.requestDTO;

import com.group08.onlineShop.model.ProductImage;
import com.group08.onlineShop.model.TypeProduct;
import lombok.Data;


@Data
public class ProductReq {
    private Long id;
    private String productName;
    private Double price;
    private Long category;
    private TypeProduct type;
    private Long productImageId;
    private String description;
}
