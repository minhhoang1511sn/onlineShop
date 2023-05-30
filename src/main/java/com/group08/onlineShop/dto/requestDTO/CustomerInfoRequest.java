package com.group08.onlineShop.dto.requestDTO;

import com.group08.onlineShop.model.Account;
import com.group08.onlineShop.model.Address;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoRequest {
    private Long account;
    @Column(length = 12)
    private String phoneNumber;
    private Address address;
    @Column(length = 45)
    private String customerName;
    private Boolean isDefault;
}
