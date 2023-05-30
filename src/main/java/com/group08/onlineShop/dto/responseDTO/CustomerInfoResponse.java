package com.group08.onlineShop.dto.responseDTO;

import com.group08.onlineShop.model.Account;
import com.group08.onlineShop.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoResponse {
    private Long id;
    private Account account;
    private String phoneNumber;
    private Address address;
    private String customerName;
    private Boolean isDefault;
}
