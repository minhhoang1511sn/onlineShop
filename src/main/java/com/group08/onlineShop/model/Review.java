package com.group08.onlineShop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review_product")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Instant reviewCreateAt;
    private String reviewContent;
    private Double reviewRate;
    private Integer reviewLike;
    private Integer reviewDislike;

    public Review(Account account, Product product, Instant reviewCreateAt, String reviewContent,
                  Double reviewRate, Integer reviewLike, Integer reviewDislike) {
        this.account = account;
        this.product = product;
        this.reviewCreateAt = reviewCreateAt;
        this.reviewContent = reviewContent;
        this.reviewRate = reviewRate;
        this.reviewLike = reviewLike;
        this.reviewDislike = reviewDislike;
    }
}
