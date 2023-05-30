package com.group08.onlineShop.controller;

import com.group08.onlineShop.dto.CategoryDTO;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/post-category")
    public ResponseEntity<?> postCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Success", HttpStatus.OK.value(), categoryService.save(categoryDTO)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping("/all-category")
    public ResponseEntity<?> getAllCategory() {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Success", HttpStatus.OK.value(), categoryService.findAll()));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @PutMapping("/update-category")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Success", HttpStatus.OK.value(), categoryService.updateCategory(categoryDTO)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/delete-category")
    public ResponseEntity<?> deleteCategory(@RequestParam Long categoryId) {
        try {
            return ResponseEntity.ok(new ApiResponse(true, "Success", HttpStatus.OK.value(), categoryService.deleteCategory(categoryId)));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
