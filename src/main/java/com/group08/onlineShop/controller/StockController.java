package com.group08.onlineShop.controller;

import com.group08.onlineShop.dto.requestDTO.StockRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1") @Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class StockController {
    private final StockService stockService;

    @GetMapping("/get-all-stocks")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllStocks() {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), stockService.getAllStock()));
    }

    @GetMapping("/get-stocks/product")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getStockByProduct(
            @RequestParam(name = "productID") Long productID
    ) {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), stockService.getStockByProduct(productID)));
    }

    @GetMapping("/get-stock")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getStockByProductAndColorAndSize(
            @RequestParam(name = "productID") Long productID,
            @RequestParam(name = "color") String color,
            @RequestParam(name = "size") String size
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(),
                stockService.getStockByProductAndColorAndSize(productID, color, size)));
    }

    @PostMapping("/post-stocks")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addProductToStock(
            @RequestBody StockRequest stockRequest
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(),
                stockService.addProductToStock(stockRequest)));
    }

    @PutMapping("/update-stock")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateStockQuantity(
            @RequestParam(name = "stockID") Long stockID,
            @RequestBody StockRequest stockRequest
    ) {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(),
                stockService.updateStockQuantity(stockID, stockRequest)));
    }

    @DeleteMapping("/delete-stock")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteStockByID(
            @RequestParam(name = "stockID") Long stockID
    ) {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(),
                stockService.deleteStockByID(stockID)));
    }
}
