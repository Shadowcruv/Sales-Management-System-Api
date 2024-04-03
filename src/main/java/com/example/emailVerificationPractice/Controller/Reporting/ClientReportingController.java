package com.example.emailVerificationPractice.Controller.Reporting;

import com.example.emailVerificationPractice.Entity.Client;
import com.example.emailVerificationPractice.Entity.Helper.SaleClientProjection;
import com.example.emailVerificationPractice.Service.ClientServiceImpl;
import com.example.emailVerificationPractice.Service.SalesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/clients-report")
@RestController
public class ClientReportingController {

    private final ClientServiceImpl clientServiceImpl;
    private final SalesServiceImpl salesServiceImpl;

    @Autowired
    public ClientReportingController(ClientServiceImpl clientServiceImpl, SalesServiceImpl salesServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
        this.salesServiceImpl = salesServiceImpl;
    }


    @GetMapping("/clients-client")
    public ResponseEntity<Map<String, Object>> generateClientReport(@RequestParam("id") Long id){
        Map<String, Object> report = new HashMap<>();
        long totalNumberOfClients = clientServiceImpl.totalNumberOfClients();
        List<SaleClientProjection> topSpendingClients = salesServiceImpl.topSpendingClients();

        report.put("totalNumberOfClients", totalNumberOfClients);
        report.put("topSpendingClients", topSpendingClients);
        report.put("clientActivity", clientServiceImpl.getClientActivity(id));
        report.put("clientsLocationStatistics", clientServiceImpl.getClientsLocationStatistics());


        return new ResponseEntity<>(report, HttpStatus.OK);
    }




}
