package com.example.emailVerificationPractice.Entity;

import javax.persistence.*;


@Entity
@Table

public class ApiRole {

    @Id
    @SequenceGenerator(
            name = "api_role_sequence",
            sequenceName = "api_role_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator =  "api_role_sequence"
    )
    private Long id;
    private String name;

    public ApiRole() {

    }

    public ApiRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
