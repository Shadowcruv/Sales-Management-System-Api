package com.example.emailVerificationPractice.Entity.Helper;

import com.example.emailVerificationPractice.Entity.Product;
import com.example.emailVerificationPractice.Entity.Sales;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleProductDTO {

    private Sales sales;
    private List<Product> products;

}
