package com.example.aviatrip.model.request.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateUserNameModel(@NotNull @Pattern(regexp = "[A-Za-z]{2,32}") String name) {}
