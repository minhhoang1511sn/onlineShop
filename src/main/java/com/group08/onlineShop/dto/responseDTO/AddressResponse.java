package com.group08.onlineShop.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressResponse {
    private Long id;
    private String city;
    private String district;
    private String commune;
    private String detailAddress;
}
