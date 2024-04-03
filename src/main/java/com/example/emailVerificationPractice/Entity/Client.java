package com.example.emailVerificationPractice.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @javax.persistence.Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    private String lastName;
    private String mobile;
    private String address;
    private String location;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Sales> sales;

}
