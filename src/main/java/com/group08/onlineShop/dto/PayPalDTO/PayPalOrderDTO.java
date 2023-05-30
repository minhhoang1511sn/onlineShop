package com.group08.onlineShop.dto.PayPalDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.group08.onlineShop.model.PayPalOrderIntent;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PayPalOrderDTO implements Serializable {
    private PayPalOrderIntent intent;
    @JsonProperty("purchase_units")
    private List<PurchaseUnit> purchaseUnits;
    @JsonProperty("application_context")
    private PayPalAppContextDTO applicationContext;
}