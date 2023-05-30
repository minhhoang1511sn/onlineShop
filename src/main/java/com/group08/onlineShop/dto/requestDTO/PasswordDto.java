package com.group08.onlineShop.dto.requestDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
public class PasswordDto {
//    private String email;
    private String newPassword;
    private String token;
}
