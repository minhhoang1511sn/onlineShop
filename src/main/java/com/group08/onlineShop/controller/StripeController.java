package com.group08.onlineShop.controller;

import com.group08.onlineShop.dto.responseDTO.StripeResponse;
import com.group08.onlineShop.service.StripeService;
import com.stripe.model.Coupon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1") @Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")

public class StripeController {
    @Value("$pk_test_51NBgcJEPN21V5XzuOw9IwutDo3lAiUgO8M1x966kELTVj0jrWsiWYkaTZqXy0BsKM8EzbmCy7JTYjADBJcfNmep200CuYLIRGd")
    private String API_PUBLIC_KEY;

    private StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @GetMapping("/")
    public String homepage() {
        return "homepage";
    }

    @GetMapping("/subscription")
    public String subscriptionPage(Model model) {
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
        return "subscription";
    }

    @GetMapping("/charge")
    public String chargePage(Model model) {
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
        return "charge";
    }

    @PostMapping("/create-subscription")
    public @ResponseBody StripeResponse createSubscription(String email, String token, String plan, String coupon) {

        if (token == null || plan.isEmpty()) {
            return new StripeResponse(false, "Stripe payment token is missing. Please try again later.");
        }

        String customerId = stripeService.createCustomer(email, token);

        if (customerId == null) {
            return new StripeResponse(false, "An error occurred while trying to create customer");
        }

        String subscriptionId = stripeService.createSubscription(customerId, plan, coupon);

        if (subscriptionId == null) {
            return new StripeResponse(false, "An error occurred while trying to create subscription");
        }

        return new StripeResponse(true, "Success! your subscription id is " + subscriptionId);
    }

    @PostMapping("/cancel-subscription")
    public @ResponseBody StripeResponse cancelSubscription(String subscriptionId) {

        boolean subscriptionStatus = stripeService.cancelSubscription(subscriptionId);

        if (!subscriptionStatus) {
            return new StripeResponse(false, "Failed to cancel subscription. Please try again later");
        }

        return new StripeResponse(true, "Subscription cancelled successfully");
    }

    @PostMapping("/coupon-validator")
    public @ResponseBody StripeResponse couponValidator(String code) {

        Coupon coupon = stripeService.retrieveCoupon(code);

        if (coupon != null && coupon.getValid()) {
            String details = (coupon.getPercentOff() == null ? "$" + (coupon.getAmountOff() / 100)
                    : coupon.getPercentOff() + "%") + "OFF" + coupon.getDuration();
            return new StripeResponse(true, details);
        }
        return new StripeResponse(false, "This coupon code is not available. This may be because it has expired or has "
                + "already been applied to your account.");
    }

    @PostMapping("/create-charge")
    public @ResponseBody StripeResponse createCharge(String email, String token) {

        if (token == null) {
            return new StripeResponse(false, "Stripe payment token is missing. please try again later.");
        }

        String chargeId = stripeService.createCharge(email, token, 999);// 9.99 usd

        if (chargeId == null) {
            return new StripeResponse(false, "An error occurred while trying to charge.");
        }

        // You may want to store charge id along with order information

        return new StripeResponse(true, "Success your charge id is " + chargeId);
    }
}
