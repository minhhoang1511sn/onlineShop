package com.group08.onlineShop.repository;

import com.group08.onlineShop.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepo extends JpaRepository<Status, Long> {
}
