package com.example.emailVerificationPractice.Service;


import com.example.emailVerificationPractice.Entity.ApiUser;
import com.example.emailVerificationPractice.Entity.DatabaseExcluded.UserPassword;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ApiService {
    String saveApiUser(ApiUser apiUser);
    ApiUser retrieveUser(String username);
    List<ApiUser> retrieveUsers();

   //TODO  List<ApiUser> retrieveUsersAdminRoleS();
    void  deleteUser(String username);

    void updateProfile(UserDetails userDetails, ApiUser apiUser);

    void updatePassword(UserDetails userDetails, UserPassword userPassword);

    String confirmTokenn(String token);
}
