package com.example.aviatrip.config.requestmodel;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateSurnameModel(@NotNull @Pattern(regexp = "[A-Za-z]{2,32}") String surname) {}
