package com.example.aviatrip.config.requestmodel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserEmailModel(@NotNull @Size(min = 4, max = 50) @Email String email) {}
