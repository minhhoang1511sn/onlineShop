package com.group08.onlineShop.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private String size;
    private String color;
    private Integer quantity;

    public Stock(Product product, String size, String color, Integer quantity) {
        this.product = product;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }
}
