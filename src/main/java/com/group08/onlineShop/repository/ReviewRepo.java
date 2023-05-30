package com.group08.onlineShop.repository;

import com.group08.onlineShop.model.Account;
import com.group08.onlineShop.model.Product;
import com.group08.onlineShop.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    Optional<List<Review>> findReviewByAccount(Account account);

    Optional<List<Review>> findReviewByProduct(Product product);
}
