package com.example.emailVerificationPractice.Repository;

import com.example.emailVerificationPractice.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {

    @Query("SELECT s FROM Product s WHERE s.id = ?1")
    Optional<Product> findOptionById(Long id);

   @Query("SELECT s FROM Product s WHERE s.name = ?1")
    Optional<Product> findOptionByProductName(String productName);

}
