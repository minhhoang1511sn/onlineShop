package com.group08.onlineShop.repository;

import com.group08.onlineShop.model.ProductImage;
import com.group08.onlineShop.service.ProductImageService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepo extends JpaRepository<ProductImage, Long> {
    ProductImage getProductImageByProduct(Long id);
}
