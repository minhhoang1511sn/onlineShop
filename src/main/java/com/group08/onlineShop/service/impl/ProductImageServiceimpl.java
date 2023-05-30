package com.group08.onlineShop.service.impl;

import com.group08.onlineShop.exception.AppException;
import com.group08.onlineShop.model.Product;
import com.group08.onlineShop.model.ProductImage;
import com.group08.onlineShop.repository.ProductImageRepo;
import com.group08.onlineShop.repository.ProductRepo;
import com.group08.onlineShop.service.Cloudinary.CloudinaryUpload;
import com.group08.onlineShop.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductImageServiceimpl implements ProductImageService {
    private final ProductRepo productRepo;
    private final ProductImageRepo productImageRepo;
    private final CloudinaryUpload cloudinaryUpload;

    @Override
    public Optional<ProductImage> getProductImg(Long id) {
        return productImageRepo.findById(id);
    }

    @Override
    public List<ProductImage> saveNewImage(Long productId, List<MultipartFile> productImageReqs, String color, Integer isDefault) {
        List<ProductImage> productImages = new ArrayList<>();
        Product product = productRepo.getReferenceById(productId);
        if(product!=null){
            productImageReqs.forEach(image -> {
                try {
                    String urlImage= cloudinaryUpload.uploadImage(image,null);
                    ProductImage productImage = new ProductImage();
                    productImage.setProduct(product);
                    productImage.setImageLink(urlImage);
                    productImage.setColor(color);
                    productImage.setIsDefault(isDefault);
                    productImages.add(productImage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } );

        }else{
            throw new AppException(400,"product is not exists");
        }
        productImageRepo.saveAll(productImages);
        List<String> urls = new ArrayList<>();
        productImages.forEach(v -> {
            urls.add(v.getImageLink());
        });
        return productImages;
    }

    @Override
    public void deleteImageProduct(Long id) {
        ProductImage productImage= productImageRepo.findById(id).orElse(null);
        if (productImage==null)
            throw new AppException(404, "ID image product not found");
        try{
            if (productImage.getImageLink().startsWith("https://res.cloudinary.com/quangdangcloud/image/upload")) {
                cloudinaryUpload.deleteImage(productImage.getImageLink());
            }
            productImageRepo.deleteById(id);
        } catch (Exception e) {
            throw new AppException(400, e.getMessage());
        }

    }


}
