package com.group08.onlineShop.dto.requestDTO;

import lombok.Data;

@Data
public class AddressRequest {
    private String city;
    private String district;
    private String commune;
    private String detailAddress;
}