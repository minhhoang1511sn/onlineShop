package com.group08.onlineShop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "country")
public class Country {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "name")
    private String name;
}
