package com.group08.onlineShop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_info")
public class CustomerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(length = 12)
    private String phoneNumber;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @Column(length = 45)
    private String customerName;
    private Boolean isDefault;
}
