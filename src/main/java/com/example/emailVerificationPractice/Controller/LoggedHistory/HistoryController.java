package com.example.emailVerificationPractice.Controller.LoggedHistory;

import com.example.emailVerificationPractice.Entity.History;
import com.example.emailVerificationPractice.Entity.Sales;
import com.example.emailVerificationPractice.Repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/history")
@RestController
public class HistoryController {

    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryController(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @GetMapping
    public ResponseEntity<List<History>> getAllSales(){
        List<History> histories= historyRepository.findAll();
        return new ResponseEntity<>(histories, HttpStatus.OK);
    }
}
