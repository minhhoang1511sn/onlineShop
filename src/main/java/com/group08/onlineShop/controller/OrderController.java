package com.group08.onlineShop.controller;

import com.group08.onlineShop.dto.requestDTO.CustomerInfoRequest;
import com.group08.onlineShop.dto.requestDTO.OrderRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.service.CustomerInfoService;
import com.group08.onlineShop.service.OrderService;
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
public class OrderController {
    private final OrderService orderService;
    @GetMapping("/get-all-order")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllOrder() {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), orderService.getAllOrder()));
    }

    @GetMapping("/get-order-by-account")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getOrderByAccount(
            @RequestParam(name="accountID") Long accountID
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), orderService.getOrderByAccount(accountID)));
    }
    @GetMapping("/get-order-status-by-account")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getOrderByStatusOfAccount(
            @RequestParam(name="accountID") Long accountID,
            @RequestParam(name="status") Long statusID
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), orderService.getOrderByStatusOfAccount(accountID, statusID)));
    }
    @GetMapping("/get-order/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getOrderByID(
            @PathVariable(name="orderID") Long orderID) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), orderService.getOrderByID(orderID)));
    }


    @PostMapping("/post-order")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> creatOrder(
            @RequestBody OrderRequest orderRequest
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), orderService.creatOrder(orderRequest)));
    }

    @PutMapping("/update-order")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateOrder(
            @RequestParam(name = "orderID") Long orderID,
            @RequestBody OrderRequest orderRequest
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(),
                orderService.updateOrder(orderID, orderRequest)));
    }

    @DeleteMapping("/delete-order")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteOrder(
            @RequestParam(name = "orderID") Long orderID
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), orderService.deleteOrder(orderID)));
    }
}
