package com.group08.onlineShop.dto.requestDTO;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    @Column(length = 20)
    private String firstName;
    @Column(length = 20)
    private String lastName;
    private String password;
}
