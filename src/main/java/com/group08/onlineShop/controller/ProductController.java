package com.group08.onlineShop.controller;

import com.group08.onlineShop.dto.requestDTO.ProductReq;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.model.Product;
import com.group08.onlineShop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/post-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postProduct(@ModelAttribute ProductReq productReq, @RequestParam List<MultipartFile> images, @RequestParam String color, @RequestParam Integer isDefault) {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Success", HttpStatus.CREATED.value(),
                    productService.saveNewProduct(productReq,images,color,isDefault)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping("/all-product")
    public ResponseEntity<?> getAllProduct(){
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Success", HttpStatus.OK.value(), productService.findAll()));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
    @GetMapping("/filterProduct")
    public ResponseEntity<?> filterProduct(@Param("keyword") String keyword)  {
        try {
            return ResponseEntity.ok(new ApiResponse(true,
                    "Success", HttpStatus.OK.value(), productService.filterProduct(keyword)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false,
                    e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
    @GetMapping("/suggest-product")
    public ResponseEntity<?> suggestProduct(@RequestBody ProductReq productReq) {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Success", HttpStatus.OK.value(), productService.suggestProduct(productReq)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping("/product/{proId}")
    public ResponseEntity<?> getProductById(@PathVariable(value = "proId") Long proId) {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Success", HttpStatus.OK.value(), productService.findById(proId)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping(value = "/update-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProduct(@ModelAttribute ProductReq productReq, @RequestParam(value = "images",required = false) List<MultipartFile> images, @RequestParam String color, @RequestParam Integer isDefault)throws ResourceNotFoundException {
        Product productResp = productService.updateProduct( productReq,images,color,isDefault);
        return new ResponseEntity<>(productResp, HttpStatus.OK);
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<?> deleteProduct(@RequestParam Long productId) {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Success", HttpStatus.OK.value(), productService.deleteProductById(productId)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
