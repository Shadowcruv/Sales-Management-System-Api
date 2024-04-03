package com.example.emailVerificationPractice.Service;

import com.example.emailVerificationPractice.Entity.*;
import com.example.emailVerificationPractice.Entity.Helper.SaleClientProjection;
import com.example.emailVerificationPractice.Entity.Helper.SaleProductDTO;
import com.example.emailVerificationPractice.Entity.Helper.SaleSellerProjection;
import com.example.emailVerificationPractice.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class SalesServiceImpl implements SalesService {

    private final ApiUserRepository apiUserRepository;
    private final SellerRepository sellerRepository;
    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    private final HistoryRepository historyRepository;

    @Autowired
    public SalesServiceImpl(ApiUserRepository apiUserRepository, SellerRepository sellerRepository, SalesRepository salesRepository, ProductRepository productRepository, ItemRepository itemRepository, HistoryRepository historyRepository) {
        this.apiUserRepository = apiUserRepository;
        this.sellerRepository = sellerRepository;
        this.salesRepository = salesRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    public List<Sales> getAllSales() {
        return salesRepository.getAllSales();
    }

    @Override
    public String saveSales(SaleProductDTO saleProductDTO) {
        Sales sales =  saleProductDTO.getSales();
        List<Product> productsPicked = saleProductDTO.getProducts();
      if (getSeller() != null) {
          try {
              sales.setCreationDate(LocalDate.now());
              List<Item> itemList = new ArrayList<>();
              int salesNo = 0;
              double totalSale = 0.0;
              for (Product productSelected : productsPicked) {
                  Product product = productRepository.findOptionByProductName(productSelected.getName().toUpperCase()).orElseThrow(()->new IllegalStateException("Product doesn't exist"));
                  if(product.getAvailableQuantity() != 0) {
                      Item item = Item.builder().item_id(product.getId()).item_name(product.getName())
                              .item_total_bought(productSelected.getSalesQuantity()).
                              item_total_amount(productSelected.getSalesQuantity() * product.getPrice()).build();

                      item.setSales(sales);
                      itemList.add(item);
                      salesNo += productSelected.getSalesQuantity();
                      totalSale += item.getItem_total_amount();
                  }else{
                      return product.getName() + " has finished";
                  }
              }

              sales.setSalesQuantity(salesNo);
              sales.setTotal(totalSale);
              sales.setItems(itemList);
              sales.setSeller(getSeller());
              salesRepository.save(sales);
              updateProducts(sales.getItems());
              History history = History.builder().activity("A new Sale was just added").build();
              historyRepository.save(history);
              return "success saved";
          } catch (Exception e) {
              return "an error occured while attempting to save : " + e.getMessage();
          }
      }else {
          return "an error occured " + getSeller();
      }
    }

    @Override
    public String updateQuatitesWithPrices(Long id, Sales sales) {
        Sales retrievedSale = salesRepository.findOptionById(id).
                orElseThrow(() -> new IllegalStateException(("No such Sale Exists")));
        retrievedSale.setSalesQuantity(sales.getSalesQuantity());
        retrievedSale.setTotal(sales.getTotal());

        String activity = "Sale with Id " + retrievedSale.getId() + " Quantity and Total was changed to " + sales.getSalesQuantity() + " " + sales.getTotal();
        History history = History.builder().activity(activity).build();
        salesRepository.save(retrievedSale);
        historyRepository.save(history);
        return "updated";
    }


    @Override
    public void deleteSales(Long id) {
        Sales sale =  salesRepository.findById(id).orElseThrow(()->new IllegalStateException("The sale does not exist"));
        History history = History.builder().activity(sale.getId()  + " was deleted from Sales Records").build();
        Sales retrievedSale = salesRepository.findOptionById(id).
                orElseThrow(() -> new IllegalStateException(("No such Sale Exists")));
        salesRepository.deleteById(id);
        historyRepository.save(history);
    }

    @Override
    public List<Sales> findAllBetweenDates(LocalDate firstDate, LocalDate lastDate) {
        return salesRepository.findAllBetweenDates(firstDate, lastDate);
    }

    @Override
    public long totalSalesBetweenTheDates(LocalDate firstDate, LocalDate lastDate) {
        return salesRepository.totalSalesBetweenTheDates(firstDate, lastDate);
    }

    @Override
    public double totalRevenueBetweenDates(LocalDate firstDate, LocalDate lastDate) {
        return salesRepository.totalRevenueBetweenDates(firstDate, lastDate);
    }

    private void updateProducts(List<Item> itemList){
        for(Item item : itemList){
            Product retrievedProduct = productRepository.findById(item.getItem_id()).orElseThrow(()->new IllegalStateException("Product does not exist"));
            retrievedProduct.setAvailableQuantity(retrievedProduct.getAvailableQuantity() - item.getItem_total_bought());
            if(retrievedProduct.getAvailableQuantity() < 0){
                throw new IllegalStateException(retrievedProduct.getName() + "quanity left is less than " + item.getItem_total_bought());
            }else{
                productRepository.save(retrievedProduct);
            }
        }
    }

    @Override
    public List<SaleSellerProjection> topPerformingSellers(LocalDate firstDate, LocalDate lastDate) {
        return salesRepository.topPerformingSellers(firstDate, lastDate);
    }

    @Override
    public List<SaleClientProjection> topSpendingClients() {
        return salesRepository.topSpendingClients();
    }

    public List<SellingProducts> topSellingProducts(){
        HashMap<Long, Integer> hashMap = new HashMap<>();
        List<Sales> saleItemProjectionList =  salesRepository.findAll();
         for(Sales saleItemProjection : saleItemProjectionList){
            for(Item item : saleItemProjection.getItems()){
                if(hashMap.containsKey(item.getItem_id())){
                    Integer value = hashMap.get(item.getItem_id());
                    hashMap.replace(item.getItem_id(), item.getItem_total_bought() + value);
                }else{
                    hashMap.put(item.getItem_id(), item.getItem_total_bought());
                }

            }
        }

        List<Map.Entry<Long, Integer>> entryList = new ArrayList<>(hashMap.entrySet());

        // Sort the list based on integer value in descending order
        Collections.sort(entryList, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        List<SellingProducts> topSellingProducts = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : entryList) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            Product product = productRepository.findById(entry.getKey()).orElseThrow(()->new IllegalStateException("product not found"));
            SellingProducts topSellingProduct = SellingProducts.builder().productName(product.getName()).noOfTimesBought(entry.getValue()).build();
            topSellingProducts.add(topSellingProduct);
        }

        return topSellingProducts;
    }

    private Seller getSeller(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return sellerRepository.findOptionByEmail(authentication.getName()).orElseThrow(()->new IllegalStateException("Seller doesn't exists"));
    }



}
