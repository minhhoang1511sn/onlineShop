package com.group08.onlineShop.dto.responseDTO;

import com.group08.onlineShop.model.Category;
import com.group08.onlineShop.model.TypeProduct;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;

@Data
public class SearchProductResp {
    private Long id;
    @Column(length = 45)
    private String productName;
    private Double price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Long category;
    private TypeProduct type;
    private List<String> imgLink;
}
