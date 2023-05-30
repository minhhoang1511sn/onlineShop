package com.group08.onlineShop.service.impl;

import com.group08.onlineShop.dto.requestDTO.PaymentRequest;
import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import com.group08.onlineShop.dto.responseDTO.PaymentResponse;
import com.group08.onlineShop.exception.ResourceNotFoundException;
import com.group08.onlineShop.model.Account;
import com.group08.onlineShop.model.CartItem;
import com.group08.onlineShop.model.Payment;
import com.group08.onlineShop.repository.AccountRepo;
import com.group08.onlineShop.repository.CartItemRepo;
import com.group08.onlineShop.repository.PaymentRepo;
import com.group08.onlineShop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepo paymentRepo;
    private final AccountRepo accountRepo;
    private final CartItemRepo cartItemRepo;
    @Override
    public List<PaymentResponse> getAllPayment() {
        List<Payment> payments = paymentRepo.findAll();
        return addPaymentResponse(payments);
    }

    @Override
    public List<PaymentResponse> getPaymentByAccount(Long accountID) throws ResourceNotFoundException {
        Account account = accountRepo.findById(accountID).orElseThrow(()
                -> new ResourceNotFoundException("Account", "accountID", accountID));
        List<Payment> payments = paymentRepo.findPaymentsByAccount(account).orElseThrow(()
                -> new ResourceNotFoundException("Payment", "accountID", account));
        return addPaymentResponse(payments);
    }

    @Override
    public PaymentResponse createPayment(PaymentRequest paymentRequest, List<Long> cartItemsID) throws ResourceNotFoundException {
        Account account = accountRepo.findById(paymentRequest.getAccount()).orElseThrow(()
                -> new ResourceNotFoundException("Account", "accountID", paymentRequest.getAccount()));
        for (Long cartItemID : cartItemsID) {
            CartItem cartItem = cartItemRepo.findById(cartItemID).orElseThrow(() ->
                    new ResourceNotFoundException("CartItem", "cartItemID", cartItemID));
            if (cartItem != null) {
                cartItemRepo.deleteById(cartItemID);
            }
        }
        var payment = Payment.builder()
                .account(account)
                .created_on(Instant.now())
                .totalPrice(paymentRequest.getTotalPrice())
                .currency(paymentRequest.getCurrency())
                .status(paymentRequest.getStatus()).build();
        paymentRepo.save(payment);
        return new PaymentResponse(payment.getId(), payment.getAccount(),
                payment.getCreated_on(), payment.getTotalPrice(), payment.getCurrency(),
                payment.getStatus(), payment.getMethod());
    }

    private List<PaymentResponse> addPaymentResponse(List<Payment> payments){
        List<PaymentResponse> paymentResponses = new ArrayList<>(payments.size());
        for(Payment payment : payments) {
            paymentResponses.add(new PaymentResponse(payment.getId(), payment.getAccount(),
                    payment.getCreated_on(), payment.getTotalPrice(), payment.getCurrency(),
                    payment.getStatus(), payment.getMethod()));
        }
        return paymentResponses;
    }
}
