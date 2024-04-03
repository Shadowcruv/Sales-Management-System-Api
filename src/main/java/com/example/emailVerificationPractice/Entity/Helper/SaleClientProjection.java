package com.example.emailVerificationPractice.Entity.Helper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleClientProjection {
    private final String name;
    private final String lastName;
    private final double total;
    private final long totalSalesQuantity;

    public SaleClientProjection(String name, String lastName, double total, long totalSalesQuantity) {
        this.name = name;
        this.lastName = lastName;
        this.total = total;
        this.totalSalesQuantity = totalSalesQuantity;
    }
}
