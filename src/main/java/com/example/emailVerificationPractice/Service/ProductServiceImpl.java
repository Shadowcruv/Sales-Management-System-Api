package com.example.emailVerificationPractice.Service;

import com.example.emailVerificationPractice.Entity.*;
import com.example.emailVerificationPractice.Repository.HistoryRepository;
import com.example.emailVerificationPractice.Repository.ItemRepository;
import com.example.emailVerificationPractice.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final HistoryRepository historyRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, HistoryRepository historyRepository, ItemRepository itemRepository) {
        this.productRepository = productRepository;
        this.historyRepository = historyRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public String saveProduct(Product product) {
        product.setQuantity(product.getInitialQuantity());
        product.setAvailableQuantity(product.getInitialQuantity());
        History history = History.builder().activity("A new Product named " + product.getName() + " was just added").build();
        productRepository.save(product);
        historyRepository.save(history);
        return "success";
    }

    @Override
    public String updateProduct(Long id, Product product) {
        Product retrievedProduct = productRepository.findOptionById(id).
                orElseThrow(() -> new IllegalStateException(("No such Product Exists")));
        retrievedProduct.setName(product.getName());
        retrievedProduct.setDescripition(product.getDescripition());
        retrievedProduct.setCategory(product.getCategory());
        retrievedProduct.setPrice(product.getPrice());
        retrievedProduct.setQuantity(product.getQuantity());
        retrievedProduct.setAvailableQuantity(retrievedProduct.getAvailableQuantity() + product.getQuantity());

        String activity = retrievedProduct.getName() + " details was changed to " + product.getName() + product.getDescripition() + product.getCategory() + product.getQuantity() + product.getPrice();
        History history = History.builder().activity(activity).build();
        productRepository.save(retrievedProduct);
        historyRepository.save(history);

        return "updated";
    }

    @Override
    public void deleteProduct(Long id) {
        Product product =  productRepository.findById(id).orElseThrow(()->new IllegalStateException("The product does not exist"));
        History history = History.builder().activity(product.getName()  + " was deleted from Products Records").build();
        productRepository.deleteById(id);
        historyRepository.save(history);
    }

    public List<ProductReport> generateProductReport(){

        List<Item> items = itemRepository.findAll();
        LinkedHashMap<Long, Integer> totalBoughthashMap =new LinkedHashMap<>();
        for(Item item : items){
            if(totalBoughthashMap.containsKey(item.getItem_id())){
                Integer value = totalBoughthashMap.get(item.getItem_id());
                totalBoughthashMap.replace(item.getItem_id(), item.getItem_total_bought() + value);
            }else{
                totalBoughthashMap.put(item.getItem_id(), item.getItem_total_bought());
            }

        }

        List<Map.Entry<Long, Integer>> entryBoughtList = new ArrayList<>(totalBoughthashMap.entrySet());

        // Sort the list based on integer value in descending order
//        Collections.sort(entryBoughtList, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        //To store total Units for Each product
        List<Integer> totalUnitsSoldForEachProduct =  new ArrayList<>();

        List<ProductReport> productReports= new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : entryBoughtList) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            Product product = productRepository.findById(entry.getKey()).orElseThrow(()->new IllegalStateException("product not found"));
            ProductReport productReport = ProductReport.builder().name(product.getName()).inventoryStatus("Available Quanities remaining is " + product.getAvailableQuantity() + " Total Units sold is " + entry.getValue()).build();
            productReports.add(productReport);
            totalUnitsSoldForEachProduct.add(entry.getValue());
        }


        LinkedHashMap<Long,Double> totalRevenuehashMap = new LinkedHashMap<>();
        for(Item item : items){
            if(totalRevenuehashMap.containsKey(item.getItem_id())){
                Double value = totalRevenuehashMap.get(item.getItem_id());
                totalRevenuehashMap.replace(item.getItem_id(), item.getItem_total_amount() + value);
            }else{
                totalRevenuehashMap.put(item.getItem_id(), item.getItem_total_amount());
            }

        }

        List<Map.Entry<Long, Double>> entryRevenueList = new ArrayList<>(totalRevenuehashMap.entrySet());

        int n = 0;
        for (Map.Entry<Long, Double> entry : entryRevenueList) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            Product product = productRepository.findById(entry.getKey()).orElseThrow(()->new IllegalStateException("product not found"));
            productReports.set(n, ProductReport.builder().name(productReports.get(n).getName())
                    .inventoryStatus(productReports.get(n).getInventoryStatus()).salesPerformance("Total revenue generated by this product is : " + entry.getValue()).
                    pricingAnalysis("Current price of the product is : " + product.getPrice() + " Average Selling price sold :" + entry.getValue()/totalUnitsSoldForEachProduct.get(n))
                    .build());
            n += 1;
        }


        return productReports;

    }
}
