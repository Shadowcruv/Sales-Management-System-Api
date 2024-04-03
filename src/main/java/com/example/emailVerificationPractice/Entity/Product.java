package com.example.emailVerificationPractice.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @javax.persistence.Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(unique = true)
    private String name;

    private String descripition;
    private String category;
    private LocalDateTime creationDate;
    private int initialQuantity;
    private int quantity;
    private int availableQuantity;
    private double price;
    private int salesQuantity;

//    @ManyToMany(mappedBy = "products", cascade = {CascadeType.PERSIST,CascadeType.MERGE},
//            fetch = FetchType.LAZY)
//    @JsonBackReference

    @OneToMany(mappedBy ="product")
    private Set<ProductSale> productSales;


}
