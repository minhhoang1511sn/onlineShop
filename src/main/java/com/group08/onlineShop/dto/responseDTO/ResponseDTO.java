package com.group08.onlineShop.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {
    private Boolean status;
    private String message;
    private Object data;
}

