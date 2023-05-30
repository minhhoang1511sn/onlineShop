package com.group08.onlineShop.dto.requestDTO;

import com.group08.onlineShop.model.Account;
import com.group08.onlineShop.model.Address;
import com.group08.onlineShop.model.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long account;
    private Instant createAt;
    private Instant updateAt;
    private String receiverName;
    private String receiverPhoneNumber;
    private Address address;
    private Double deliveryChargers;
    private Double totalPrice;
    private Long status;
}
