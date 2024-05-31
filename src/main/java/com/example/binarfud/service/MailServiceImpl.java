package com.example.binarfud.service;

import com.example.binarfud.model.dto.user.UserRegisterRequestDto;
import com.example.binarfud.model.entity.account.ERole;
import com.example.binarfud.model.entity.account.User;
import com.example.binarfud.repository.RoleRepository;
import com.example.binarfud.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired Environment env;
    @Autowired JavaMailSender javaMailSender;
    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;
    @Autowired BCryptPasswordEncoder passwordEncoder;
    @Autowired ModelMapper modelMapper;

    @Override
    public void registerUser(UserRegisterRequestDto userRegisterRequestDto, String role) {
        User user = modelMapper.map(userRegisterRequestDto, User.class);
        user.setId(UUID.randomUUID());
        user.setActive(false);
        user.setOtp(generateOtp());
        user.setOtpExpirationTime(LocalDateTime.now().plusMinutes(10));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ERole eRole = role.equals("seller") ? ERole.ROLE_SELLER : ERole.ROLE_CUSTOMER;
        user.getRoles().add(roleRepository.findByName(eRole));
        userRepository.save(user);
        sendOtpEmail(user.getEmail(), user.getOtp());
    }

    @Override
    public String generateOtp() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    @Override
    public void sendOtpEmail(String mailAddress, String otp){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty("spring.mail.username"));
        message.setTo(mailAddress);
        message.setSubject("OTP Code for Binarfud");
        message.setText("Your OTP code is " + otp);
        javaMailSender.send(message);
    }

    @Override
    public boolean validateOtp(String email, String otp) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getOtp().equals(otp) && user.getOtpExpirationTime().isAfter(LocalDateTime.now())) {
                user.setActive(true);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public void sendPasswordResetOtp(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setOtp(generateOtp());
            user.setOtpExpirationTime(LocalDateTime.now().plusMinutes(10));
            userRepository.save(user);
            sendOtpEmail(email, user.getOtp());
        }
    }

    @Override
    public boolean resetPassword(String email, String otp, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getOtp().equals(otp) && user.getOtpExpirationTime().isAfter(LocalDateTime.now())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}