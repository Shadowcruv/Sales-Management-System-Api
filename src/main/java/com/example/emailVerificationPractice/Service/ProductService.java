package com.example.emailVerificationPractice.Service;

import com.example.emailVerificationPractice.Entity.Product;
import com.example.emailVerificationPractice.Entity.ProductReport;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    String saveProduct(Product product);

    String updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    List<ProductReport> generateProductReport();
}
