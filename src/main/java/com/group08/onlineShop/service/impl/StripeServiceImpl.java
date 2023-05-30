package com.group08.onlineShop.service.impl;

import com.group08.onlineShop.service.StripeService;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Coupon;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {
    @Value("$sk_test_51NBgcJEPN21V5XzuPpSNc7PZO1H8kFteYF2vbLTnn20tY4ujH9Pvm5gjamNEFUNgoKjMuCzTju6vsaKE2Lv5T2YM00zJAhpLr9\n")
    private String API_SECRET_KEY;

    @Override
    public String createCustomer(String email, String token) {
        String id = null;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Map<String, Object> customerParams = new HashMap<>();
            // add customer unique id here to track them in your web application
            customerParams.put("description", "Customer for " + email);
            customerParams.put("email", email);

            customerParams.put("source", token); // ^ obtained with Stripe.js
            //create a new customer
            Customer customer = Customer.create(customerParams);
            id = customer.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    @Override
    public String createSubscription(String customerId, String plan, String coupon) {
        String subscriptionId = null;

        try {
            Stripe.apiKey = API_SECRET_KEY;

            Map<String, Object> item = new HashMap<>();
            item.put("plan", plan);

            Map<String, Object> items = new HashMap<>();
            items.put("0", item);

            Map<String, Object> params = new HashMap<>();
            params.put("customer", customerId);
            params.put("items", items);

            if (!coupon.isEmpty()) {
                params.put("coupon", coupon);
            }

            Subscription subscription = Subscription.create(params);

            subscriptionId = subscription.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subscriptionId;
    }

    @Override
    public boolean cancelSubscription(String subscriptionId) {
        boolean subscriptionStatus;

        try {
            Subscription subscription = Subscription.retrieve(subscriptionId);
            subscription.cancel();
            subscriptionStatus = true;
        } catch (Exception e) {
            e.printStackTrace();
            subscriptionStatus = false;
        }
        return subscriptionStatus;
    }

    @Override
    public Coupon retrieveCoupon(String code) {
        try {
            Stripe.apiKey = API_SECRET_KEY;
            return Coupon.retrieve(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String createCharge(String email, String token, int amount) {
        String chargeId = null;

        try {
            Stripe.apiKey = API_SECRET_KEY;

            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("description","Charge for "+email);
            chargeParams.put("currency","usd");
            chargeParams.put("amount",amount);
            chargeParams.put("source",token);

            Charge charge = Charge.create(chargeParams);

            chargeId = charge.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chargeId;
    }
}
