package com.example.binarfud.model.dto.user;

import com.example.binarfud.model.entity.account.Role;
import com.example.binarfud.model.entity.account.User;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;

    private String username;
    private String email;
    private String password;
    private List<Role> roles;
}
