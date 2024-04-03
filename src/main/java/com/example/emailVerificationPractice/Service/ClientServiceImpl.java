package com.example.emailVerificationPractice.Service;

import com.example.emailVerificationPractice.Entity.*;
import com.example.emailVerificationPractice.Entity.Helper.ClientLocationSaleProjection;
import com.example.emailVerificationPractice.Repository.ClientRepository;
import com.example.emailVerificationPractice.Repository.HistoryRepository;
import com.example.emailVerificationPractice.Repository.ProductRepository;
import com.example.emailVerificationPractice.Repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;

    private final HistoryRepository historyRepository;
    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, HistoryRepository historyRepository, ProductRepository productRepository, SalesRepository salesRepository) {
        this.clientRepository = clientRepository;
        this.historyRepository = historyRepository;
        this.productRepository = productRepository;
        this.salesRepository = salesRepository;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public String saveClient(Client client) {
      History history = History.builder().activity("A new client named " + client.getName() + " was just created").build();
      clientRepository.save(client);
      historyRepository.save(history);

      return "success";
    }

    @Override
    public String updateClient(Long id, Client client) {
        Client retrievedClient = clientRepository.findOptionById(id).
                orElseThrow(() -> new IllegalStateException(("No such Product Exists")));

        retrievedClient.setName(client.getName());
        retrievedClient.setMobile(client.getMobile());
        retrievedClient.setLastName(client.getLastName());
        retrievedClient.setEmail(client.getEmail());
        retrievedClient.setAddress(client.getAddress());

        String activity = retrievedClient.getName() + " details was changed to " + client.getName() + client.getMobile() + client.getLastName() + client.getEmail() + client.getAddress();
        History history = History.builder().activity(activity).build();
        clientRepository.save(retrievedClient);
        historyRepository.save(history);
        return "updated";
    }

    @Override
    public void deleteClient(Long id) {

       Client client =  clientRepository.findById(id).orElseThrow(()->new IllegalStateException("The client does not exist"));
       History history = History.builder().activity(client.getName() + " " + client.getLastName() + " was deleted from Client Records").build();

       clientRepository.deleteById(id);
       historyRepository.save(history);


    }

    public long totalNumberOfClients(){
        return clientRepository.totalNumberOfClients();
    }


    public ClientActivity getClientActivity(Long id){
        int totalSales = 0;
        HashMap<Long, Integer> hashMap = new HashMap<>();
        List<Sales> saleItemProjectionList =  salesRepository.findAll();
        for(Sales saleItemProjection : saleItemProjectionList){
            if(Objects.equals(saleItemProjection.getClient().getId(), id)) {
                totalSales += 1;
                for (Item item : saleItemProjection.getItems()) {
                    if (hashMap.containsKey(item.getItem_id())) {
                        Integer value = hashMap.get(item.getItem_id());
                        hashMap.replace(item.getItem_id(), item.getItem_total_bought() + value);
                    } else {
                        hashMap.put(item.getItem_id(), item.getItem_total_bought());
                    }

                }
            }
        }

        List<Map.Entry<Long, Integer>> entryList = new ArrayList<>(hashMap.entrySet());

        // Sort the list based on integer value in descending order
        Collections.sort(entryList, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        List<SellingProducts> topSellingProducts = new ArrayList<>();
        List<String> historyOfProductsBought = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : entryList) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            Product product = productRepository.findById(entry.getKey()).orElseThrow(()->new IllegalStateException("product not found"));
            SellingProducts topSellingProduct = SellingProducts.builder().productName(product.getName()).noOfTimesBought(entry.getValue()).build();
            topSellingProducts.add(topSellingProduct);
        }
        for (SellingProducts sellingProducts : topSellingProducts){
            String clientProducthistory = "has bought " + sellingProducts.getProductName() + " " + sellingProducts.getNoOfTimesBought() + " times";
            historyOfProductsBought.add(clientProducthistory);
        }
        String clientName = clientRepository.findById(id).orElseThrow(()-> new IllegalStateException("Client not Found")).getName() + " " + clientRepository.findById(id).orElseThrow(()-> new IllegalStateException("Client not Found")).getLastName();
        ClientActivity clientActivity = ClientActivity.builder().name(clientName).totalSales(totalSales).productsBoughtHistory(historyOfProductsBought).build();

        return clientActivity;
    }

    public List<String> getClientsLocationStatistics(){
        List<String> allClientsLocationStatistics = new ArrayList<>();
        List<ClientLocationSaleProjection> clientLocationSaleProjections = clientRepository.getClientLocationStatistics();
        for(ClientLocationSaleProjection clientLocationSaleProjection : clientLocationSaleProjections){
            String locationStatics = clientLocationSaleProjection.getCount() + " clients that has purchased from our store stay in " +  clientLocationSaleProjection.getAddress();
            allClientsLocationStatistics.add(locationStatics);
        }

        return allClientsLocationStatistics;
    }


}
