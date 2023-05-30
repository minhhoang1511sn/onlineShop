package com.group08.onlineShop.repository;

import com.group08.onlineShop.model.Account;
import com.group08.onlineShop.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    Optional<List<Payment>> findPaymentsByAccount(Account account);
}
