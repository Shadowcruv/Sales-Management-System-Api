package com.example.emailVerificationPractice.Ctoken;

import com.example.emailVerificationPractice.Entity.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {

    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("update ConfirmationToken c set c.confirmedAt = ?2 where c.token = ?1 ")
    void updateConfirmedAt(String token, LocalDateTime confirmedAt);


    @Transactional
    @Modifying
    @Query("update ConfirmationToken c set c.expiresAt = ?2 where c.apiUser = ?1 ")
    void updateExpiredAt(ApiUser apiUser, LocalDateTime expiredAt);

    @Transactional
    @Modifying
    @Query("update ConfirmationToken c set c.createdAt = ?2 where c.apiUser = ?1 ")
    void updateCreatedAt(ApiUser apiUser, LocalDateTime createdAt);

    @Transactional
    @Modifying
    @Query("update ConfirmationToken c set c.token = ?2 where c.apiUser = ?1")
    int updateToken(ApiUser apiUser, String Token);


    ConfirmationToken findByapiUser(ApiUser apiUser);


}
