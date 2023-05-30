package com.group08.onlineShop.controller;


import com.group08.onlineShop.dto.requestDTO.ProductImageReq;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.dto.responseDTO.ResponseDTO;
import com.group08.onlineShop.dto.requestDTO.ProductImageReq;
import com.group08.onlineShop.dto.responseDTO.ResponseDTO;
import com.group08.onlineShop.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class ProductImageController {
    private final ProductImageService productImageService;
    @GetMapping(value = "productImage/{id}")
    private ResponseEntity<?> getImageProduct(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Success", HttpStatus.OK.value(), productImageService.getProductImg(id)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }


    @PostMapping(value = "/admin/productImageUrl", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<?> addImageProduct(@RequestParam Long productId, @RequestParam List<MultipartFile> images,@RequestParam String color,@RequestParam Integer isDefault){
            productImageService.saveNewImage(productId,images,color,isDefault);
            return ResponseEntity.ok(new ResponseDTO(true,"Success",null));
    }

    @DeleteMapping("/admin/productImageUrl/{id}")
    private ResponseEntity<?> deleteImageProduct(@PathVariable Long id){
        productImageService.deleteImageProduct(id);
        return ResponseEntity.ok(new ResponseDTO(true,"Success",null));

    }
}
