package com.example.emailVerificationPractice.Entity;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReport {
    private String name;
    private String inventoryStatus;
    private String salesPerformance;
    private String pricingAnalysis;


}
