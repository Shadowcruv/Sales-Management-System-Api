package com.example.emailVerificationPractice.Controller.general;


import com.example.emailVerificationPractice.Entity.ApiUser;
import com.example.emailVerificationPractice.Service.ApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/registration")
@RestController
public class RegistrationPage {

    private final ApiServiceImpl apiService;

    @Autowired
    public RegistrationPage(ApiServiceImpl apiService) {
        this.apiService = apiService;
    }

    @PostMapping
    public ResponseEntity<String> saveCApiUser(@RequestBody ApiUser apiUser){
        return new ResponseEntity<>(apiService.saveApiUser(apiUser), HttpStatus.CREATED);

    }

    //Confirming the token
    @GetMapping(path = "confirm")
    public String confirmToken(@RequestParam("token") String token){
        return apiService.confirmTokenn(token);
    }

}
