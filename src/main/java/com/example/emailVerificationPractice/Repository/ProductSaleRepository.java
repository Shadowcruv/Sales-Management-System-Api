package com.example.emailVerificationPractice.Repository;

import com.example.emailVerificationPractice.Entity.ProductSale;
import com.example.emailVerificationPractice.Entity.Sales;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductSaleRepository extends JpaRepository<ProductSale,Long> {

//    @Query("SELECT p.name, SUM(s.quantity) as total_quantity FROM ProductSale sp JOIN Sales s ON sp.sales.id = s.id JOIN Product p ON p.id = sp.product.id GROUP BY p.id ORDER BY total_quantity DESC ")
//    List<ProductSale> findMostOccurringBetweenDates(LocalDateTime firstDate, LocalDateTime endDate);
}
