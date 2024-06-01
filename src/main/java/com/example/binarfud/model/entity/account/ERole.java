package com.example.binarfud.model.entity.account;

public enum ERole {
    ROLE_CUSTOMER,
    ROLE_SELLER;

    public static ERole toERole(String roleString) {
        try {
            return ERole.valueOf(roleString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + roleString);
        }
    }
}
