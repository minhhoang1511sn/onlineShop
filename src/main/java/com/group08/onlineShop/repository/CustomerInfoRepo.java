package com.group08.onlineShop.repository;


import com.group08.onlineShop.model.Account;
import com.group08.onlineShop.model.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerInfoRepo extends JpaRepository<CustomerInfo, Long> {
    List<CustomerInfo> findCustomerInfosByAccount(Account account);
    Optional<CustomerInfo> findCustomerInfoByAccountAndIsDefault(Account account, Boolean isDefault);
}
