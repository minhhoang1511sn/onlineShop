package com.group08.onlineShop.service;


import com.group08.onlineShop.model.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductImageService {
    Optional<ProductImage> getProductImg(Long id);
    List<ProductImage> saveNewImage(Long productId, List<MultipartFile> productImageReqs, String color, Integer isDefault);

    void deleteImageProduct(Long id);


}
