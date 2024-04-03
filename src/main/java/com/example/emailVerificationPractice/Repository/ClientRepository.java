package com.example.emailVerificationPractice.Repository;

import com.example.emailVerificationPractice.Entity.Client;
import com.example.emailVerificationPractice.Entity.Helper.ClientLocationSaleProjection;
import com.example.emailVerificationPractice.Entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository  extends JpaRepository<Client,Long> {
    @Query("SELECT s FROM Client s WHERE s.id = ?1")
    Optional<Client> findOptionById(Long id);

    @Query("SELECT new com.example.emailVerificationPractice.Entity.Helper.ClientLocationSaleProjection(Count(c), c.address) FROM Client c GROUP BY c.address")
    List<ClientLocationSaleProjection> getClientLocationStatistics();

    @Query("SELECT COUNT(s) FROM Client s")
    long totalNumberOfClients();
}
