package com.group08.onlineShop.service;

import com.stripe.model.Coupon;

public interface StripeService {
    String createCustomer(String email, String token);
    String createSubscription(String customerId, String plan, String coupon);
    boolean cancelSubscription(String subscriptionId);
//    Input coupon's code
    Coupon retrieveCoupon(String code);
    String createCharge(String email, String token, int amount);
}
