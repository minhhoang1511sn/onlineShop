package com.group08.onlineShop.dto.responseDTO;

import com.group08.onlineShop.model.Address;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class AccountResponseDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Long role_id;
    private String password;
    private Boolean active;
    private String phoneNumber;
    private Address address;
    private String customerName;

    public AccountResponseDTO(Long id, String email, String firstName, String lastName, Long role_id, Boolean active, String phoneNumber, Address address, String customerName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role_id = role_id;
        this.active = active;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.customerName = customerName;
    }
}
