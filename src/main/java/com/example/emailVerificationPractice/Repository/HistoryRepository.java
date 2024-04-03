package com.example.emailVerificationPractice.Repository;

import com.example.emailVerificationPractice.Entity.Client;
import com.example.emailVerificationPractice.Entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History,Long> {


}
