package com.group08.onlineShop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;
    private Instant createAt;
    private Instant updateAt;
    private String receiverName;
    private String receiverPhoneNumber;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    private Double deliveryChargers;
    private Double totalPrice;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "status_id")
    private Status status;
}
