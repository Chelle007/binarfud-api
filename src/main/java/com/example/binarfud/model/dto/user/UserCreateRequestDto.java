package com.example.binarfud.model.dto.user;

import com.example.binarfud.model.entity.account.Role;
import com.example.binarfud.model.entity.account.User;
import lombok.Data;

import java.util.List;

@Data
public class UserCreateRequestDto {
    private String username;
    private String email;
    private String password;
    private List<Role> roles;
}
