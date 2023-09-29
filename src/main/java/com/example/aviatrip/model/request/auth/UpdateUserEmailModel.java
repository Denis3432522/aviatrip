package com.example.aviatrip.model.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserEmailModel(@NotNull @Size(min = 4, max = 50) @Email String email) {}
