package com.example.binarfud.service;

import com.example.binarfud.model.dto.user.UserRegisterRequestDto;
import com.example.binarfud.model.entity.account.User;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void registerUser(UserRegisterRequestDto userRegisterRequestDto, String role);
    String generateOtp();
    void sendOtpEmail(String mailAddress, String otp);
    boolean validateOtp(String email, String otp);
    void sendPasswordResetOtp(String email);
    boolean resetPassword(String email, String otp, String newPassword);
}
