package com.group08.onlineShop.dto.responseDTO;

import com.group08.onlineShop.model.Account;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.Instant;

@Data
public class ReviewResponse {
    private Long id;
    private Long account;
    private Long product;
    private Instant createAt;
    private String content;
    private Double rate;
    private Integer like;
    private Integer dislike;

    public ReviewResponse(Long id, Long account, Long product, Instant createAt, String content, Double rate, Integer like, Integer dislike) {
        this.id = id;
        this.account = account;
        this.product = product;
        this.createAt = createAt;
        this.content = content;
        this.rate = rate;
        this.like = like;
        this.dislike = dislike;
    }
}
