package com.group08.onlineShop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.descriptor.jdbc.TinyIntJdbcType;
import org.modelmapper.spi.StrongTypeConditionalConverter;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_image")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JsonBackReference
    private Product product;
    private String imageLink;
    private Integer isDefault;
    private String color;
}
