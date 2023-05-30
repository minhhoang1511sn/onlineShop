package com.group08.onlineShop.service.impl;

import com.group08.onlineShop.dto.requestDTO.ProductReq;
import com.group08.onlineShop.dto.responseDTO.SearchProductResp;
import com.group08.onlineShop.exception.AppException;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.model.Category;
import com.group08.onlineShop.model.Product;
import com.group08.onlineShop.model.ProductImage;
import com.group08.onlineShop.model.TypeProduct;
import com.group08.onlineShop.repository.CategoryRepo;
import com.group08.onlineShop.repository.ProductImageRepo;
import com.group08.onlineShop.repository.ProductRepo;
import com.group08.onlineShop.service.ProductImageService;
import com.group08.onlineShop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceIpml implements ProductService {
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final ProductImageService productImageService;
    private final ModelMapper modelMapper;

    @Override
    public Product saveNewProduct(ProductReq productReq, List<MultipartFile> productImageReqs, String color, Integer isDefault) {
        Category category = categoryRepo.findById(productReq.getCategory()).orElse(null);
        if(category!=null){
            Product product = new Product();
            product.setCategory(category);
            product.setProductName(productReq.getProductName());
            product.setType(productReq.getType());
            product.setPrice(productReq.getPrice());
            product.setDescription(productReq.getDescription());
            productRepo.save(product);
            List<ProductImage> result = productImageService.saveNewImage(product.getId(),productImageReqs,color,isDefault);
            product.setProductImage(result);
            return productRepo.save(product);
        }
        return null;

    }
    @Override
    public List<SearchProductResp> getProductByKeyword(String keyword, Long manufacturerId, Long categoryId, TypeProduct type) {
        Category category = null;
        if (categoryId != 0 && categoryRepo.existsById(categoryId))
            category = categoryRepo.getReferenceById(categoryId);
        List<Product> products = productRepo.findAllByKeyword(keyword.toLowerCase(), category,type);

        return processResponseProductList(products);
    }


    @Override
    public List<SearchProductResp> search(String keyword) {
        List<Product> products = productRepo.search(keyword);

        return processResponseProductList(products);
    }

    List<SearchProductResp> processResponseProductList(List<Product> productsList) {
        List<SearchProductResp> productRespList = new ArrayList<>();
        for (Product product : productsList) {
            SearchProductResp searchProductRespDTO = modelMapper.map(product, SearchProductResp.class);
            productRespList.add(searchProductRespDTO);
        }
        return productRespList;
    }



    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Product findById(Long productId) throws ResourceNotFoundException {
        var product = productRepo.findById(productId).
                orElseThrow(() -> new ResourceNotFoundException("Product", "productID", Long.toString(productId)));
        return product;
    }

    @Override
    public Product updateProduct(ProductReq productReq,List<MultipartFile> productImageReqs, String color, Integer isDefault) throws ResourceNotFoundException {
        Product productUpdate = findById(productReq.getId());
        Category category = categoryRepo.findById(productReq.getCategory()).orElseThrow(()
        -> new ResourceNotFoundException("Category", "categoryID", productReq.getCategory()));
        productUpdate.setCategory(category);
        productUpdate.setPrice(productReq.getPrice());
        productUpdate.setProductName(productReq.getProductName());
        productUpdate.setType(productReq.getType());
        productUpdate.setDescription(productUpdate.getDescription());
        if(productImageReqs!=null){
            List<ProductImage> p = productImageService.saveNewImage(productUpdate.getId(),productImageReqs, color, isDefault);
            productUpdate.setProductImage(p);
        }

        productRepo.save(productUpdate);
        return productUpdate;
    }
    @Override
    public List<Product> suggestProduct(ProductReq productReq) throws ResourceNotFoundException {
        Product product = findById(productReq.getId());
        List<Product> productAll = findAll();
        List<Product> sugProd = new ArrayList<>();
        productAll.forEach(
                pA ->{
                    if(pA.getCategory() == product.getCategory())
                    {
                        sugProd.add(pA);
                    }
                    else if(pA.getType() == product.getType())
                    {
                        sugProd.add(pA);
                    }
                }
        );
        return sugProd;
    }

    @Override
    public List<Product> filterProduct(String keyword) throws ResourceNotFoundException {
        if (keyword != null) {
            return productRepo.search(keyword);
        }
        return productRepo.findAll();
    }

    @Override
    public boolean deleteProductById(Long id) {
        Product productDelete = productRepo.findById(id).orElse(null);
        if (productDelete != null) {
            productRepo.deleteById(id);
            return true;
        } else {
            throw new AppException(404, "Product ID not found");
        }
    }
}
