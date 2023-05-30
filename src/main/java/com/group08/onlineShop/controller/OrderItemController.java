package com.group08.onlineShop.controller;

import com.group08.onlineShop.dto.requestDTO.OrderItemRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class OrderItemController {
    private final OrderItemService orderItemService;
    @GetMapping("/get-all-order-item")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllOrderItem() {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), orderItemService.getAllOrderItem()));
    }

    @GetMapping("/get-order-item-by-order")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getOrderItemByOrder(
            @RequestParam(name="orderID") Long orderID
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), orderItemService.getOrderItemByOrder(orderID)));
    }

    @GetMapping("/get-order-item/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getOrderItemByID(
            @PathVariable(name="orderItemID") Long orderItemID) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), orderItemService.getOrderItemByID(orderItemID)));
    }


    @PostMapping("/post-order-item")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> creatOrder(
            @RequestBody OrderItemRequest orderItemRequest
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), orderItemService.createOrderItem(orderItemRequest)));
    }

    @PutMapping("/update-order-item")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateOrderItem(
            @RequestParam(name = "orderItemID") Long orderItemID,
            @RequestBody OrderItemRequest orderItemRequest
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(),
                orderItemService.updateOrderItem(orderItemID, orderItemRequest)));
    }

    @DeleteMapping("/delete-order-item")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteOrder(
            @RequestParam(name = "orderItemID") Long orderItemID
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), orderItemService.deleteOrderItem(orderItemID)));
    }
}
