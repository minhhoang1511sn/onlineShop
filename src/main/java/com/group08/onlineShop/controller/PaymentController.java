package com.group08.onlineShop.controller;

import com.group08.onlineShop.dto.requestDTO.PaymentRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/get-all-payments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllPayments() {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), paymentService.getAllPayment()));
    }

    @GetMapping("/get-payments-by-account")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getPaymentsByAccount(
            @RequestParam(name="accountID") Long accountID
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), paymentService.getPaymentByAccount(accountID)));
    }

    @PostMapping("/post-payment")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> creatPayment(
            @RequestBody PaymentRequest paymentRequest,
            @RequestParam(name="cartItemsID") List<Long> cartItemsID
    ) throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponse(true,
                "Success", HttpStatus.OK.value(), paymentService.createPayment(paymentRequest, cartItemsID)));
    }
}
