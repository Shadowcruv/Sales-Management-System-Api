package com.example.emailVerificationPractice.Service;

import com.example.emailVerificationPractice.Entity.*;
import com.example.emailVerificationPractice.Entity.Helper.SaleClientProjection;
import com.example.emailVerificationPractice.Entity.Helper.SaleProductDTO;
import com.example.emailVerificationPractice.Entity.Helper.SaleSellerProjection;
import com.example.emailVerificationPractice.Repository.*;
//import com.example.emailVerificationPractice.Repository.RoomRepository;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Service {

//    @Autowired
//    private RoomRepository roomRepository;

    @Autowired
    private ApiUserRepository apiUserRepository;

    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private SalesServiceImpl salesServiceImp;

    @Autowired
    private ClientServiceImpl clientServiceImpl;

    @Autowired
    private ProductServiceImpl productServiceImpl;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;


//    @Test
//    public void saveApiUser(){
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        TransactionStatus status = transactionManager.getTransaction(def);
//
//        try {
//            Room room = roomRepository.findByRoomNo("A3/02");
//            Room managedRoom = entityManager.merge(room);
//
//            ApiUser apiUser = ApiUser.builder().userName("eka").password("chika").room(room).build();
//            apiUserRepository.save(apiUser);
//
//            // Additional logic within the transaction if needed
//
//            transactionManager.commit(status);  // Commit the transaction
//        } catch (Exception e) {
//            // Handle exceptions if needed
//            transactionManager.rollback(status);  // Rollback the transaction
//        }
//
//    }

//    @Test
//    public void saveRoom(){
//        Room room = Room.builder().roomNo("A3/01").build();
//        Room room1 = Room.builder().roomNo("A3/03").build();
//        Room room2 = Room.builder().roomNo("A3/02").build();
//        List<Room> roomList = new ArrayList<>();
//        roomList.add(room);
//        roomList.add(room1);
//        roomList.add(room2);
//        roomRepository.saveAll(roomList);
//    }

    @Test
    public void saveClient(){
        Client client1 = Client.builder().name("Mark").address("Abuja").location("America").build();
        Client client2 = Client.builder().name("Emeka").address("Delta").location("Nigeria").build();
        Client client3 = Client.builder().name("Ebuka").address("Abuja").location("America").build();
        Client client4 = Client.builder().name("Chidi").address("Delta").location("Ondo").build();
        Client client5 = Client.builder().name("Marcel").address("Abuja").location("America").build();
        List<Client> clientList = new ArrayList<>();
        clientList.add(client1);
        clientList.add(client2);
        clientList.add(client3);
        clientList.add(client4);
        clientList.add(client5);

        clientRepository.saveAll(clientList);
    }
    @Test
    public void saveSeller(){
        Seller seller1 = Seller.builder().fullName("Choima").build();
        Seller seller2 = Seller.builder().fullName("Judith").build();
        Seller seller3 = Seller.builder().fullName("Anabel").build();
        Seller seller4 = Seller.builder().fullName("Chikaodili").build();
        Seller seller5 = Seller.builder().fullName("Chimeriem").build();
        Seller seller6 = Seller.builder().fullName("Onioma").build();

        List<Seller> sellerList = new ArrayList<>();
        sellerList.add(seller1);
        sellerList.add(seller2);
        sellerList.add(seller4);
        sellerList.add(seller5);
        sellerList.add(seller6);
        sellerList.add(seller3);

        sellerRepository.saveAll(sellerList);
    }
    @Test
    public void saveProduct(){

        Product product1 = Product.builder().descripition("Fridge").price(1000).availableQuantity(8).build();
        Product product2 = Product.builder().descripition("AirConditioner").price(5000).availableQuantity(8).build();
        Product product3 = Product.builder().descripition("Television").price(1500).availableQuantity(8).build();
        Product product4 = Product.builder().descripition("Speaker").price(2000).availableQuantity(8).build();
        Product product5 = Product.builder().descripition("Microwave Oven").price(1500).availableQuantity(8).build();
        Product product6 = Product.builder().descripition("Fans").price(2000).availableQuantity(8).build();
        Product product7 = Product.builder().descripition("Generators").price(3000).availableQuantity(8).build();
        List<Product> productList = List.of(product1,product2,product3,product4,product5,product6,product7);
        productRepository.saveAll(productList);
    }


//    @Test
//    public void saveSales1(){
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        TransactionStatus status = transactionManager.getTransaction(def);
//
//    try {
//
//
//        Client retrievedClient1 = clientRepository.findById(3L).orElseThrow(() -> new IllegalArgumentException("No such user exists"));
//        Seller retrievedSeller1 = sellerRepository.findById(4L).orElseThrow(() -> new IllegalArgumentException("No such user exists"));
//        Product retrievedProduct1 = productRepository.findById(2L).orElseThrow(() -> new IllegalArgumentException("No such product exists"));
//        Product retrievedProduct2 = productRepository.findById(3L).orElseThrow(() -> new IllegalArgumentException("No such product exists"));
//        Product retrievedProduct3 = productRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("No such product exists"));
//        retrievedProduct1.setSalesQuantity(4);
//        retrievedProduct2.setSalesQuantity(5);
//        retrievedProduct3.setSalesQuantity(3);
//        List<Product> products = List.of(retrievedProduct1, retrievedProduct2, retrievedProduct3);
//        Sales sales1 = Sales.builder().creationDate(LocalDate.now()).client(retrievedClient1).seller(retrievedSeller1).build();
//        SaleProductDTO saleProductDTO = SaleProductDTO.builder().sales(sales1).products(products).build();
//
//        salesServiceImp.saveSales(saleProductDTO);
//        transactionManager.commit(status);
//    } catch (Exception e) {
//            // Handle exceptions if needed
//            transactionManager.rollback(status);  // Rollback the transaction
//        }
//
//    }
//
//    @Test
//    public void saveSales2(){
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        TransactionStatus status = transactionManager.getTransaction(def);
//
//        try {
//        Client retrievedClient1 = clientRepository.findById(2L).orElseThrow(()-> new IllegalArgumentException("No such user exists"));
//        Seller retrievedSeller1 = sellerRepository.findById(2L).orElseThrow(()-> new IllegalArgumentException("No such user exists"));
//        Product retrievedProduct1 = productRepository.findById(4L).orElseThrow(()-> new IllegalArgumentException("No such product exists"));
//        Product retrievedProduct2 = productRepository.findById(5L).orElseThrow(()-> new IllegalArgumentException("No such product exists"));
//        Product retrievedProduct3 = productRepository.findById(1L).orElseThrow(()-> new IllegalArgumentException("No such product exists"));
//        retrievedProduct1.setSalesQuantity(1);
//        retrievedProduct2.setSalesQuantity(3);
//        retrievedProduct3.setSalesQuantity(5);
//            List<Product> products = List.of(retrievedProduct1, retrievedProduct2, retrievedProduct3);
//            Sales sales1 = Sales.builder().creationDate(LocalDate.now()).client(retrievedClient1).seller(retrievedSeller1).build();
//            SaleProductDTO saleProductDTO = SaleProductDTO.builder().sales(sales1).products(products).build();
//
//
//            salesServiceImp.saveSales(saleProductDTO);
//        transactionManager.commit(status);
//    } catch (Exception e) {
//        // Handle exceptions if needed
//        transactionManager.rollback(status);  // Rollback the transaction
//    }
//    }
//
//    @Test
//    public void saveSales3(){
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        TransactionStatus status = transactionManager.getTransaction(def);
//
//        try {
//       // Client retrievedClient1 = clientRepository.findById(1L).orElseThrow(()-> new IllegalArgumentException("No such user exists"));
//        Client retrievedClient1 = Client.builder().name("Ikenna").address("Kaduna").build();
//        Seller retrievedSeller1 = sellerRepository.findById(3L).orElseThrow(()-> new IllegalArgumentException("No such user exists"));
//        Product retrievedProduct1 = productRepository.findById(2L).orElseThrow(()-> new IllegalArgumentException("No such product exists"));
//        Product retrievedProduct2 = productRepository.findById(6L).orElseThrow(()-> new IllegalArgumentException("No such product exists"));
//        Product retrievedProduct3 = productRepository.findById(1L).orElseThrow(()-> new IllegalArgumentException("No such product exists"));
//        retrievedProduct1.setSalesQuantity(2);
//        retrievedProduct2.setSalesQuantity(4);
//        retrievedProduct3.setSalesQuantity(3);
//            List<Product> products = List.of(retrievedProduct1, retrievedProduct2, retrievedProduct3);
//            Sales sales1 = Sales.builder().creationDate(LocalDate.now()).client(retrievedClient1).seller(retrievedSeller1).build();
//            SaleProductDTO saleProductDTO = SaleProductDTO.builder().sales(sales1).products(products).build();
//
//            salesServiceImp.saveSales(saleProductDTO);
//        transactionManager.commit(status);
//        } catch (Exception e) {
//            // Handle exceptions if needed
//            transactionManager.rollback(status);  // Rollback the transaction
//        }
//    }

    @Test
    public void edit(){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            salesServiceImp.updateQuatitesWithPrices(4L, Sales.builder().salesQuantity(2).total(45000).build());

            transactionManager.commit(status);
        } catch (Exception e) {
            // Handle exceptions if needed
            transactionManager.rollback(status);  // Rollback the transaction
        }
    }

    @Test
    public void totalSalebtwdates(){
        Long value;
        value = salesServiceImp.totalSalesBetweenTheDates(LocalDate.of(2024,03,29), LocalDate.of(2024, 03,31));

    }

    @Test
    public void totalRevenuebtwDate(){
        double total;
        total = salesServiceImp.totalRevenueBetweenDates(LocalDate.of(2024,03,29), LocalDate.of(2024, 03,31));

    }

    @Test
    public void topSellingProducts(){
        List<SellingProducts> productsList = salesServiceImp.topSellingProducts();
        System.out.println("nice");
    }

    @Test
    public void topPerformingSellers(){
        List<SaleSellerProjection> saleSellerProjections = salesServiceImp.topPerformingSellers(LocalDate.of(2024,03,29), LocalDate.of(2024, 03,31));
        System.out.println("nice");
    }

    @Test
    public void topSpendingClients(){
        List<SaleClientProjection> saleSellerProjections = salesServiceImp.topSpendingClients();
        System.out.println("nice");
    }

    @Test
    public void getClientActivity(){
        ClientActivity clientActivity = clientServiceImpl.getClientActivity(8L);
        System.out.println("nice");
    }

    @Test
    public void getClientLocationStatics(){
        List<String> clientLocationStatistics = clientServiceImpl.getClientsLocationStatistics();
        System.out.println("nice");
    }

    @Test
    public void getProductReport(){
        List<ProductReport> productReports = productServiceImpl.generateProductReport();
        System.out.println("nice");
    }
//
//    @Test
//    public void saveRoom1(){
//
//        Room room2 = Room.builder().roomNo("A3/05").build();
//        Room room3 = Room.builder().roomNo("A3/06").build();
//        List<Room> roomList = new ArrayList<>();
//        roomList.add(room2);
//        roomList.add(room3);
//        roomRepository.saveAll(roomList);
//    }
//
//    @Test
//    @Transactional
//    public void saveApiUser1(){
//
//            Room room = roomRepository.findByRoomNo("A3/05");
//           Room managedRoom =  entityManager.merge(room);
//
//            ApiUser apiUser = ApiUser.builder().userName("price").password("chika").room(managedRoom).build();
//            apiUserRepository.save(apiUser);
//
//

//    }



}
