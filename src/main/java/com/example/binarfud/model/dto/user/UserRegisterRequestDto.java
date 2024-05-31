package com.example.binarfud.model.dto.user;

import lombok.Data;

@Data
public class UserRegisterRequestDto {
    private String username;
    private String email;
    private String password;
}
