package com.group08.onlineShop.repository;

import com.group08.onlineShop.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

    @Query(value = "SELECT * FROM token u WHERE u.code = ?1",
            nativeQuery = true)
    Token findByTokenString(String token);
}
