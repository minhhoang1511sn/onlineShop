package com.group08.onlineShop.dto.PayPalDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientTokenDTO {
    @JsonProperty("client_token")
    private String clientToken;
    @JsonProperty("expires_in")
    private Long expiresIn;
}
