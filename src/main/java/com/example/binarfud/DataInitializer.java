package com.example.binarfud;

import com.example.binarfud.model.entity.account.ERole;
import com.example.binarfud.model.entity.account.Role;
import com.example.binarfud.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer {
    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        Arrays.stream(ERole.values()).forEach(this::addRoleIfNotExists);
    }

    private void addRoleIfNotExists(ERole role) {
        if (!roleRepository.existsByName(role)) {
            Role newRole = Role.builder()
                    .name(role)
                    .build();
            roleRepository.save(newRole);
        }
    }
}
