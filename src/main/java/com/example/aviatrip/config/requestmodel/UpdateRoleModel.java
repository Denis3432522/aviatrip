package com.example.aviatrip.config.requestmodel;

import jakarta.validation.constraints.NotBlank;

public class UpdateRoleModel {

    @NotBlank
    private String role;

    public String getRole() {
        return role;
    }
}
