package com.group08.onlineShop.repository;

import com.group08.onlineShop.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByEmail(String email);
    Boolean existsByEmail(String email);

    Optional<Object> findByEmail(String username);
}
