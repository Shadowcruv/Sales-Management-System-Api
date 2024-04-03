package com.example.emailVerificationPractice.Controller.client;

import com.example.emailVerificationPractice.Entity.Client;
import com.example.emailVerificationPractice.Entity.Sales;
import com.example.emailVerificationPractice.Service.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/client")
@RestController
public class ClientController {

    private final ClientServiceImpl clientServiceImpl;

    @Autowired
    public ClientController(ClientServiceImpl clientServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
    }

    @GetMapping("/get-all-clients")
    public ResponseEntity<List<Client>> getAllClient(){
        List<Client> clientList= clientServiceImpl.getAllClients();
        return new ResponseEntity<>(clientList, HttpStatus.OK);
    }

    @PostMapping("/add-client")
    public ResponseEntity<String> addClient(@RequestBody Client client){
        return new ResponseEntity<>(clientServiceImpl.saveClient(client), HttpStatus.CREATED);
    }

    @PutMapping("/update-client/")
    public ResponseEntity<String> updateClient(@RequestParam("id") Long id, @RequestBody Client client){
        return new ResponseEntity<>(clientServiceImpl.updateClient(id, client), HttpStatus.OK);
    }


    @DeleteMapping("/delete-client/")
    @PreAuthorize("hasAnyAuthority('role_Admin')")
    public void deleteClient(@RequestParam("id") Long id){
        clientServiceImpl.deleteClient(id);
    }


}
