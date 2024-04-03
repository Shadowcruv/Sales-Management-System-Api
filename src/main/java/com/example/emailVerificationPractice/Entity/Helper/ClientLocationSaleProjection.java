package com.example.emailVerificationPractice.Entity.Helper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientLocationSaleProjection {
    private final Long count;
    private final String address;


    public ClientLocationSaleProjection(Long count, String address) {
        this.count = count;
        this.address = address;
    }
}
