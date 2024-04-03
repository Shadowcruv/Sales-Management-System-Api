package com.example.emailVerificationPractice.Service;

import com.example.emailVerificationPractice.Entity.Helper.SaleClientProjection;
import com.example.emailVerificationPractice.Entity.Helper.SaleProductDTO;
import com.example.emailVerificationPractice.Entity.Helper.SaleSellerProjection;
import com.example.emailVerificationPractice.Entity.Product;
import com.example.emailVerificationPractice.Entity.Sales;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SalesService {
    List<Sales> getAllSales();
    String saveSales(SaleProductDTO saleProductDTO);
    String updateQuatitesWithPrices(Long id, Sales sales);
    void deleteSales(Long id);
    List<Sales> findAllBetweenDates(LocalDate firstDate, LocalDate lastDate);
    long totalSalesBetweenTheDates(LocalDate firstDate, LocalDate lastDate);
    double totalRevenueBetweenDates(LocalDate firstDate, LocalDate lastDate);
    List<SaleSellerProjection> topPerformingSellers(LocalDate firstDate, LocalDate lastDate);
    List<SaleClientProjection> topSpendingClients();
}
