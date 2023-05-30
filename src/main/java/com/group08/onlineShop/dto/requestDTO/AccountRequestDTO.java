package com.group08.onlineShop.dto.requestDTO;

import com.group08.onlineShop.model.Address;
import com.group08.onlineShop.model.Role;
import lombok.Data;

@Data
public class AccountRequestDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private Boolean active;
    private String phoneNumber;
    private Address address;
    private String customerName;
}
