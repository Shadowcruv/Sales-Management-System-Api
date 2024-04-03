package com.example.emailVerificationPractice.Service;

import com.example.emailVerificationPractice.Ctoken.ConfirmationToken;
import com.example.emailVerificationPractice.Ctoken.ConfirmationTokenService;
import com.example.emailVerificationPractice.EmailValidate.EmailValidator;
import com.example.emailVerificationPractice.Entity.ApiRole;
import com.example.emailVerificationPractice.Entity.ApiUser;
import com.example.emailVerificationPractice.Entity.DatabaseExcluded.UserPassword;
import com.example.emailVerificationPractice.Entity.Seller;
import com.example.emailVerificationPractice.Repository.ApiRoleRepository;
import com.example.emailVerificationPractice.Repository.ApiUserRepository;
import com.example.emailVerificationPractice.Repository.SellerRepository;
import com.example.emailVerificationPractice.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ApiServiceImpl implements ApiService, UserDetailsService {


    private final ApiUserRepository apiUserRepository;

    private final SellerRepository sellerRepository;
    private final ApiRoleRepository apiRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;




    @Autowired
    public ApiServiceImpl(ApiUserRepository apiUserRepository,
                          SellerRepository sellerRepository, ApiRoleRepository apiRoleRepository, PasswordEncoder passwordEncoder,
                          EmailValidator emailValidator,
                          ConfirmationTokenService confirmationTokenService, EmailSender emailSender) {
        this.apiUserRepository = apiUserRepository;
        this.sellerRepository = sellerRepository;
        this.apiRoleRepository = apiRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailValidator = emailValidator;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
    }


    @Override
    public String saveApiUser(ApiUser apiUser) {
        return validate(apiUser);
    }

    public String validate(ApiUser apiUser){
        validateEmail(apiUser);
        return checkingUser(apiUser);


    }

    public void  validateEmail(ApiUser apiUser){
        boolean isValidEmail = emailValidator.validateEmail(apiUser.getUserName());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
    }

    public List<ApiUser> getAdminUsers(){
        List<ApiUser> adminUsers = apiUserRepository.findByroleName("Admin");
        return adminUsers;
    }

    private String checkingUser(ApiUser apiUser) {
        Optional<ApiUser> validation =  apiUserRepository.findOptionUserName(apiUser.getUserName());
        if (validation.isPresent()){
            if(apiUser.getUserName().equalsIgnoreCase(apiUserRepository.findByuserName(apiUser.getUserName()).getUserName())
            ){
                return "email already taken";
            }else {
                saveApiUser1(apiUser);
                afterSave(apiUser);
                return "success";
            }

        }
        saveApiUser1(apiUser);
        afterSave(apiUser);
        return "success";
    }

    public void saveApiUser1(ApiUser apiUser){
        apiUser.setPassword(passwordEncoder.encode(apiUser.getPassword()));
        apiUser.setAuthoritiess(List.of(new ApiRole("role_Admin")));  //it's meant to be role_Seller but for Test purpose make it role Admin

        Seller seller = Seller.builder().email(apiUser.getUserName()).
                address(apiUser.getAddress()).mobile(apiUser.getMobile()).
                fullName(apiUser.getFullName()).build();
        sellerRepository.save(seller);
        apiUserRepository.save(apiUser);
    }

    @Transactional
    public String alternateAfterSave(ApiUser apiUser){
        String token = UUID.randomUUID().toString();

        confirmationTokenService.updateToken(apiUser, token);
        confirmationTokenService.updateCreatedAt(apiUser,LocalDateTime.now());
        confirmationTokenService.updateExpiredAt(apiUser,LocalDateTime.now().plusMinutes(15L));


        String link = "http://localhost:8083/registration/confirm?token=" + token; //token
        emailSender.send(apiUser.getUserName(),
                buildEmail(apiUser.getUserName(), link));


        return "email sent";
    }

    public String afterSave(ApiUser apiUser){
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token,
                                                                    LocalDateTime.now(),
                                                                    LocalDateTime.now().plusMinutes(15L),
                                                                    apiUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "https://www.postman.com/"; // + token
//        emailSender.send(apiUser.getUserName(),
//                buildEmail(apiUser.getUserName(), link));
        // A confirmation email has been sent to you.
        return token;

    }




    public String buildEmail(String userName,String link){
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + userName + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. No need to click any link to activate account. This is a demo project. Go and login, your account is already activated: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">No need to activate anything. Just go to your Postman and continue testing my sales-management Api</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }


    @Override
    public ApiUser retrieveUser(String username) {
        ApiUser apiUser = apiUserRepository.findByuserName(username);

        return apiUser;
    }




    @Override
    public List<ApiUser> retrieveUsers() {
      List<ApiUser> apiUsers = apiUserRepository.findAll();
        return apiUsers;
    }

    //TODO List<ApiUser> retrieveUsersAdminRoleS()
//    public List<ApiUser> retrieveUsersAdminRoleS(){
//        List<ApiUser> apiUsers = apiUserRepository.findUsersByAdmin();
//        return apiUsers;
//    }


    public ApiUser updateUser( String email, ApiUser apiUser){
        ApiUser retrievedUser =  apiUserRepository.findOptionUserName(email).
                orElseThrow(()-> new IllegalStateException("No such User Exists"));
        retrievedUser.setFullName(apiUser.getFullName());
        retrievedUser.setMobile(apiUser.getMobile());
        retrievedUser.setAddress(apiUser.getAddress());

        Seller seller = sellerRepository.findOptionByEmail(retrievedUser.getUserName()).
                orElseThrow(()-> new IllegalStateException("No such Seller Exists"));
        seller.setFullName(retrievedUser.getFullName());
        seller.setMobile(retrievedUser.getMobile());
        seller.setAddress(retrievedUser.getAddress());

        sellerRepository.save(seller);
        return apiUserRepository.save(retrievedUser);

    }


    public ApiUser updateUserRoleAdmin( String email){
        ApiUser retrievedStudent =  apiUserRepository.findOptionUserName(email).
                orElseThrow(()-> new IllegalStateException("No such User Exists"));

        retrievedStudent.setAuthoritiess(List.of(new ApiRole("role_Admin")));

        return apiUserRepository.save(retrievedStudent);

    }


    public ApiUser updateUserRoleToSeller( String email){
        ApiUser retrievedStudent =  apiUserRepository.findOptionUserName(email).
                orElseThrow(()-> new IllegalStateException("No such User Exists"));

        retrievedStudent.setAuthoritiess(List.of(new ApiRole("role_Seller")));

        return apiUserRepository.save(retrievedStudent);

    }


    public void deleteUser(String username) {
     //   apiUserRepository.deleteByuserName(username);
        ApiUser apiUser = apiUserRepository.findByuserName(username);
        apiUserRepository.deleteById(apiUser.getId());
      // apiUserRepository.remove
    }

    @Override
    public void updateProfile(UserDetails userDetails, ApiUser apiUser) {
        ApiUser retrievedUser = apiUserRepository.findOptionUserName(userDetails.getUsername()).
                orElseThrow(()-> new IllegalStateException("No such User Exists"));

        retrievedUser.setFullName(apiUser.getFullName());
        retrievedUser.setMobile(apiUser.getMobile());
        retrievedUser.setAddress(apiUser.getAddress());

        Seller seller = sellerRepository.findOptionByEmail(retrievedUser.getUserName()).
                orElseThrow(()-> new IllegalStateException("No such Seller Exists"));
        seller.setFullName(retrievedUser.getFullName());
        seller.setMobile(retrievedUser.getMobile());
        seller.setAddress(retrievedUser.getAddress());
        apiUserRepository.save(retrievedUser);
    }

    @Override
    public void updatePassword(UserDetails userDetails, UserPassword userPassword) {
        ApiUser retrievedUser = apiUserRepository.findOptionUserName(userDetails.getUsername()).
                orElseThrow(()-> new IllegalStateException("No such User Exists"));

        retrievedUser.setPassword(passwordEncoder.encode(userPassword.getText()));

        apiUserRepository.save(retrievedUser);
    }

    @Override
    public String confirmTokenn(String token) {
      //  ConfirmationToken confirmationToken = confirmationTokenService
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(()-> new IllegalStateException("token not found"));

        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiresAt = confirmationToken.getExpiresAt();

        if(expiresAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");

        }
        confirmationTokenService.setConfirmedAt(token);
        apiUserRepository.enable(confirmationToken.getApiUser().getUserName());

        return "confirmed";
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApiUser apiUser = apiUserRepository.findByuserName(username);
        if (apiUser == null){
            System.out.println("ApiUser not found in the database");
            throw new UsernameNotFoundException("ApiUser not found in the database");
        }else {
            System.out.println("User found in the database: "+ username);
        }
        Collection<SimpleGrantedAuthority> authoritiessList = new ArrayList<>();
        apiUser.getAuthoritiess().forEach(apiRole->{
            authoritiessList.add(new SimpleGrantedAuthority(apiRole.getName()));
        });


       /*
       Collection<ApiRole> authoritiess = apiUser.getAuthoritiess();
        for(ApiRole apiRole: authoritiess){
            authoritiessList.add(new SimpleGrantedAuthority(apiRole.getName()));

        }
      */

        return new org.springframework.security.core.userdetails.User(apiUser.getUserName(),apiUser.getPassword(),apiUser.getEnabled(), true,
                true, true, authoritiessList);
    }



}