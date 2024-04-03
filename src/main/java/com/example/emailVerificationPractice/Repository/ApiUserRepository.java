package com.example.emailVerificationPractice.Repository;


import com.example.emailVerificationPractice.Entity.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {
    ApiUser findByuserName(String username);
    List<ApiUser> findByroleName(String username);
    void deleteByuserName(String username);

    @Query("SELECT s FROM ApiUser s WHERE s.userName = ?1")
    Optional<ApiUser> findOptionUserName(String username);

    @Transactional
    @Modifying
    @Query("update ApiUser s set s.enabled = true where s.userName = ?1")
    void enable(String userName);

    //TODO: write query and method to return apiUsers with adminRole List<ApiUser> findUsersByAdmin();




}
