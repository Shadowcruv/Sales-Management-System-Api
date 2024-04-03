package com.example.emailVerificationPractice.Ctoken;

import com.example.emailVerificationPractice.Entity.ApiUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getToken(String token){
       return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        confirmationTokenRepository.updateConfirmedAt(token,
                LocalDateTime.now());
    }
    public int updateToken(ApiUser apiUser, String token) {
        return confirmationTokenRepository.updateToken(apiUser, token);
    }

    public void updateExpiredAt(ApiUser apiUser, LocalDateTime localDateTime){
        confirmationTokenRepository.updateExpiredAt(apiUser, localDateTime);
    }

    public void updateCreatedAt(ApiUser apiUser, LocalDateTime localDateTime){
        confirmationTokenRepository.updateCreatedAt(apiUser, LocalDateTime.now());
    }


}
