package com.example.emailVerificationPractice.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
    @javax.persistence.Id
    @SequenceGenerator(
            name = "client_sequence",
            sequenceName = "client_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator =  "client_sequence"
    )
    private Long id;
    private String fullName;
    private int Age;
    private String mobile;

    @Column(unique = true)
    private String email;

    private String address;
}
