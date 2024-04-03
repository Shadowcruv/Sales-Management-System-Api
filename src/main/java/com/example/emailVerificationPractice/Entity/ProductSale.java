package com.example.emailVerificationPractice.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSale {

    @javax.persistence.Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sales_id")
    private Sales sales;
}
