package com.group08.onlineShop.controller;

import com.group08.onlineShop.dto.requestDTO.CartItemRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.service.CartItemService;
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
public class CartItemController {
    private final CartItemService cartItemService;

    @GetMapping("/get-cart-items")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllCartItems() throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(),
                cartItemService.getAllCartItems()));
    }

    @GetMapping("/get-cart-item")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getCartItemByID(
            @RequestParam(name = "cartItemID") Long cartItemID
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(),
                cartItemService.getCartItemByID(cartItemID)));
    }

    @GetMapping("/get-cart-item/account")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getCartItemsByAccount(
            @RequestParam(name = "accountID") Long accountID
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(),
                cartItemService.getCartItemsByAccount(accountID)));
    }

    @PostMapping("/add-cart-item")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addCartItem(
            @RequestBody CartItemRequest cartItemRequest
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(),
                cartItemService.addCartItem(cartItemRequest)));
    }

    @PutMapping("/update-cart-item")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateCartItem(
            @RequestParam(name = "cartItemID") Long cartItemID,
            @RequestBody CartItemRequest cartItemRequest
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(),
                cartItemService.updateCartItem(cartItemID, cartItemRequest)));
    }

    @DeleteMapping("/delete-cart-item")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteCartItemByCartItemID(
            @RequestParam(name = "cartItemID") Long cartItemID
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(),
                cartItemService.deleteCartItemByCartItemID(cartItemID)));
    }
}
