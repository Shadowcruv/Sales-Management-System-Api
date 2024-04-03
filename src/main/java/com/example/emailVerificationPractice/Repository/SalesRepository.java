package com.example.emailVerificationPractice.Repository;

import com.example.emailVerificationPractice.Entity.ApiUser;
import com.example.emailVerificationPractice.Entity.Helper.ClientLocationSaleProjection;
import com.example.emailVerificationPractice.Entity.Helper.SaleClientProjection;
import com.example.emailVerificationPractice.Entity.Helper.SaleItemProjection;
import com.example.emailVerificationPractice.Entity.Helper.SaleSellerProjection;
import com.example.emailVerificationPractice.Entity.Sales;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalesRepository  extends JpaRepository<Sales,Long> {

    @Query("SELECT s FROM Sales s WHERE s.id = ?1")
    Optional<Sales> findOptionById(Long id);

    @Query("SELECT s FROM Sales s")
    List<Sales> getAllSales();

    @Query("SELECT s FROM Sales s WHERE s.creationDate BETWEEN :firstDate AND :lastDate")
    List<Sales> findAllBetweenDates(LocalDate firstDate, LocalDate lastDate);

    @Query("SELECT COUNT(s) FROM Sales s WHERE s.creationDate BETWEEN :firstDate AND :lastDate")
    long totalSalesBetweenTheDates(LocalDate firstDate, LocalDate lastDate);

    @Query("SELECT SUM(s.total) FROM Sales s WHERE s.creationDate BETWEEN :firstDate AND :lastDate")
    double totalRevenueBetweenDates(LocalDate firstDate, LocalDate lastDate);

    @Query("SELECT new com.example.emailVerificationPractice.Entity.Helper.SaleSellerProjection(se.fullName, COUNT(s)) FROM Sales s JOIN Seller se ON s.seller.id = se.id WHERE s.creationDate BETWEEN :firstDate AND :lastDate GROUP BY se.id ORDER BY COUNT(s) DESC ")
    List<SaleSellerProjection> topPerformingSellers(LocalDate firstDate, LocalDate lastDate);


    @Query("SELECT new com.example.emailVerificationPractice.Entity.Helper.SaleClientProjection(c.name, c.lastName, SUM(s.total), SUM(s.salesQuantity)) FROM Sales s JOIN Client c ON s.client.id = c.id GROUP BY c.id ORDER BY SUM(s.total) DESC ")
    List<SaleClientProjection> topSpendingClients();

   // @Query("Select new com.example.emailVerificationPractice.Entity.Helper.SaleItemProjection(s.client.id, s.items) from Sales s ")
    @EntityGraph(attributePaths = {"client", "items"})
    List<Sales> findAll();

//    @Query("SELECT new com.example.emailVerificationPractice.Entity.Helper.ClientLocationSaleProjection(COUNT(c)) FROM Sales s JOIN Client c ON s.client.id = c.id GROUP BY c.address ")
//    List<ClientLocationSaleProjection> getClientsLocationActivity();




}
