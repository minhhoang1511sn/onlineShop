package com.group08.onlineShop.dto.responseDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.group08.onlineShop.dto.PayPalDTO.LinkDTO;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayPalOrderResponseDTO {
    private String id;
    private PayPalOrderStatus status;
    private List<LinkDTO> links;
}
