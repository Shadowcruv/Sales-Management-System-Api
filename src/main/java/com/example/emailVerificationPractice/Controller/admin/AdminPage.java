package com.example.emailVerificationPractice.Controller.admin;

import com.example.emailVerificationPractice.Entity.ApiUser;
import com.example.emailVerificationPractice.Service.ApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin-page")
@RestController
public class AdminPage {

    private final ApiServiceImpl apiService;


    @Autowired
    public AdminPage(ApiServiceImpl apiService ) {
        this.apiService = apiService;

    }

    @GetMapping
    @PreAuthorize("hasAuthority('role_Admin')")
    public List<ApiUser> retrieveApiUsersList(){
        List<ApiUser> apiUsers = apiService.retrieveUsers();
        return apiUsers;
    }

    @PostMapping("/save-api-user")
    public void saveApiUser(@RequestBody ApiUser apiUser){
        apiService.saveApiUser(apiUser);
        System.out.println("Updated Successfully");
    }


    @PutMapping("/update-user/{email}")
    public ApiUser updateUser(@RequestParam("email") String email, @RequestBody ApiUser apiUser){

       return apiService.updateUser(email,apiUser);

    }
    @PutMapping("/update-to-admin")
    public ApiUser updateUserRoleAdmin(@RequestParam("email") String email){

        return apiService.updateUserRoleAdmin(email);

    }

    @PutMapping("/update-to-seller")
    public ApiUser updateUserRoleToSeller(@RequestParam("email") String email){

        return apiService.updateUserRoleToSeller(email);

    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('role_Admin')")
    public void deleteUser(@RequestParam("sellerUsername") String username){

        apiService.deleteUser(username);
    }


}
