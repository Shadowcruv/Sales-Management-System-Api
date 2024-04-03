package com.example.emailVerificationPractice.Service;

import com.example.emailVerificationPractice.Entity.Client;
import com.example.emailVerificationPractice.Entity.ClientActivity;


import java.util.List;

public interface ClientService {
    List<Client> getAllClients();

    String saveClient(Client client);

    String updateClient(Long id, Client client);

    void deleteClient(Long id);
    long totalNumberOfClients();

    ClientActivity getClientActivity(Long id);

    List<String> getClientsLocationStatistics();
}
