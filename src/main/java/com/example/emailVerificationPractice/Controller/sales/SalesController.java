package com.example.emailVerificationPractice.Controller.sales;

import com.example.emailVerificationPractice.Entity.ApiUser;
import com.example.emailVerificationPractice.Entity.Helper.SaleProductDTO;
import com.example.emailVerificationPractice.Entity.Product;
import com.example.emailVerificationPractice.Entity.Sales;
import com.example.emailVerificationPractice.Service.SalesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/sales")
@RestController
public class SalesController {

    private final SalesServiceImpl salesServiceImpl;

    @Autowired
    public SalesController(SalesServiceImpl salesServiceImpl) {
        this.salesServiceImpl = salesServiceImpl;
    }

    @GetMapping("/get-all-sales")
    public ResponseEntity<List<Sales>>  getAllSales(){
        List<Sales> salesList= salesServiceImpl.getAllSales();
        return new ResponseEntity<>(salesList, HttpStatus.OK);
    }

    @PostMapping("/add-sale")
    public ResponseEntity<String> addSale(@RequestBody SaleProductDTO sales, @AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(salesServiceImpl.saveSales(sales), HttpStatus.CREATED);
    }

    @PutMapping("/update-sale")
    public ResponseEntity<String> updateSale(@RequestParam("id") Long id, @RequestBody Sales sales){
        return new ResponseEntity<>(salesServiceImpl.updateQuatitesWithPrices(id, sales), HttpStatus.OK);
    }


    @DeleteMapping("/delete-sale/{id}")
    @PreAuthorize("hasAnyAuthority('role_Admin')")
    public void deleteSales(@PathVariable Long id){
        salesServiceImpl.deleteSales(id);
    }


}
