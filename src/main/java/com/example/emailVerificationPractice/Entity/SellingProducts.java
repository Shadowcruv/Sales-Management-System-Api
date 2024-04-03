package com.example.emailVerificationPractice.Entity;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellingProducts {
    private int noOfTimesBought;
    private String productName;

}
