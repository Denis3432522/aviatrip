package com.example.aviatrip.config.requestmodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserPasswordModel(@NotBlank @Size(min=8, max = 120) String password) {}
