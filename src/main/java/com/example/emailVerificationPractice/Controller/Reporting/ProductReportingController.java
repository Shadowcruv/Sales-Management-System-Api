package com.example.emailVerificationPractice.Controller.Reporting;

import com.example.emailVerificationPractice.Entity.Client;
import com.example.emailVerificationPractice.Entity.ProductReport;
import com.example.emailVerificationPractice.Service.ClientServiceImpl;
import com.example.emailVerificationPractice.Service.ProductServiceImpl;
import com.example.emailVerificationPractice.Service.SalesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/product-report")
@RestController
public class ProductReportingController {
    private final ClientServiceImpl clientServiceImpl;
    private final SalesServiceImpl salesServiceImpl;

    private final ProductServiceImpl productServiceImpl;

    @Autowired
    public ProductReportingController(ClientServiceImpl clientServiceImpl, SalesServiceImpl salesServiceImpl, ProductServiceImpl productServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
        this.salesServiceImpl = salesServiceImpl;
        this.productServiceImpl = productServiceImpl;
    }


    @GetMapping
    public ResponseEntity<List<ProductReport>> generateProductReport(){

        return new ResponseEntity<>(productServiceImpl.generateProductReport(), HttpStatus.OK);
    }
}
