package com.example.emailVerificationPractice.Controller.product;

import com.example.emailVerificationPractice.Entity.Client;
import com.example.emailVerificationPractice.Entity.Product;
import com.example.emailVerificationPractice.Service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/product")
@RestController
public class ProductController {

    private final ProductServiceImpl productServiceÏmpl;

    @Autowired
    public ProductController(ProductServiceImpl productServiceÏmpl) {
        this.productServiceÏmpl = productServiceÏmpl;
    }

    @GetMapping("/get-all-products")
    public ResponseEntity<List<Product>> getAllPtroduct(){
        List<Product> productList= productServiceÏmpl.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/add-sale")
    public ResponseEntity<String> addProduct(@RequestBody Product product){
        return new ResponseEntity<>(productServiceÏmpl.saveProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/update-product")
    public ResponseEntity<String> updateProduct(@RequestParam("id") Long id, @RequestBody Product product){
        return new ResponseEntity<>(productServiceÏmpl.updateProduct(id, product), HttpStatus.OK);
    }


    @DeleteMapping("/delete-product")
    @PreAuthorize("hasAnyAuthority('role_Admin')")
    public void deleteClient(@RequestParam("id") Long id){
        productServiceÏmpl.deleteProduct(id);
    }
}
