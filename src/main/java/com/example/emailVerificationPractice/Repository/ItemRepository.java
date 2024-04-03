package com.example.emailVerificationPractice.Repository;

import com.example.emailVerificationPractice.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {


}
