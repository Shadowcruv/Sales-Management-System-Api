package com.example.emailVerificationPractice.Security;

import com.example.emailVerificationPractice.Entity.ApiRole;
import com.example.emailVerificationPractice.Entity.ApiUser;
import com.example.emailVerificationPractice.Repository.ApiUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class ExtraConfig {

    private final ApiUserRepository apiUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ExtraConfig(ApiUserRepository apiUserRepository, PasswordEncoder passwordEncoder) {
        this.apiUserRepository = apiUserRepository;
        this.passwordEncoder = passwordEncoder;
    }




    @Bean
    public ApiUser apiUser() {
        Collection<ApiRole> xc = (Collection<ApiRole>) new ArrayList<ApiRole>();
        xc.add(new ApiRole("role_Admin"));
        xc.add(new ApiRole("role_Seller"));
        ApiUser apiUser = apiUserRepository.findByuserName("samuel");
        if(apiUser != null){
             System.out.println("a data exists of samuel already");
        }else {
            apiUserRepository.save(
//                new ApiUser("samuel", passwordEncoder.encode("chidubem"),xc,true));

                    ApiUser.builder().userName("samuel").password(passwordEncoder.encode("chidubem")).
                            authoritiess(xc).enabled(true).build());
        }

        return null;
    }

}
