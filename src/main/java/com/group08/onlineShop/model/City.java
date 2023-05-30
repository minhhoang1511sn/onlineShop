package com.group08.onlineShop.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "city")
public class City {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "cityname")
    private String name;

}