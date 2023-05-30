package com.group08.onlineShop.dto.responseDTO;

import com.group08.onlineShop.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private Long id;
    private Account account;
    private Instant created_on;
    private Double totalPrice;
    private String currency;
    private String status;
    private String method;
}
