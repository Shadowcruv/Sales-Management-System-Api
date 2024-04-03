package com.example.emailVerificationPractice.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sales {
    @javax.persistence.Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private LocalDate creationDate;
    private double total;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH
            },
            fetch = FetchType.LAZY

    )
    @JoinColumn(name = "client_Id")
    private Client client;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH
            },
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "seller_Id")
    private Seller seller;

    private int salesQuantity;

////    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
////    @JoinTable(name = "SALES_PRODUCTS",
////                joinColumns = {
////                    @JoinColumn(name = "sales_id", referencedColumnName = "id")
////                },
////                inverseJoinColumns = {
////                    @JoinColumn(name = "product_id", referencedColumnName = "id")
////                })
//
//    @JsonManagedReference

    @OneToMany(mappedBy = "sales")
    private Set<ProductSale> productSales;

    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL)
    private List<Item> items;

}

