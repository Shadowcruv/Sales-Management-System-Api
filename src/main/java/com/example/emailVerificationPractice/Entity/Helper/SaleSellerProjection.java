package com.example.emailVerificationPractice.Entity.Helper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleSellerProjection {
    private final String fullName;
    private final long totalSalesCompleted;

    public SaleSellerProjection(String fullName, long totalSalesCompleted) {
        this.fullName = fullName;
        this.totalSalesCompleted = totalSalesCompleted;
    }


}
