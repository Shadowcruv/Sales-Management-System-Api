package com.example.emailVerificationPractice.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class History {
    @javax.persistence.Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;
    private String activity;


}
