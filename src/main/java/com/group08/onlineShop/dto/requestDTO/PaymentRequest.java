package com.group08.onlineShop.dto.requestDTO;

import lombok.Data;

import java.time.Instant;

@Data
public class PaymentRequest {
    private Long account;
    private Instant created_on;
    private Double totalPrice;
    private String currency;
    private String status;
    private String method;
}
