package com.group08.onlineShop.service;

import com.group08.onlineShop.dto.requestDTO.PaymentRequest;
import com.group08.onlineShop.dto.responseDTO.PaymentResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;

import java.util.List;

public interface PaymentService {
    List<PaymentResponse> getAllPayment();
    List<PaymentResponse> getPaymentByAccount(Long accountID) throws ResourceNotFoundException;
    PaymentResponse createPayment(PaymentRequest paymentRequest, List<Long> cartItemsID) throws ResourceNotFoundException;
}
