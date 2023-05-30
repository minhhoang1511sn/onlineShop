package com.group08.onlineShop.dto.requestDTO;

import lombok.Data;

@Data
public class ProductImageReq {
    private String urlImage;
    private Long productId;
    private String color;
    private Integer isDefault;
}
