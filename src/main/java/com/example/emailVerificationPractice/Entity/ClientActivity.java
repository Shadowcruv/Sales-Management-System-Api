package com.example.emailVerificationPractice.Entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientActivity {
    String name;
    int totalSales;
    List<String> productsBoughtHistory;
}
