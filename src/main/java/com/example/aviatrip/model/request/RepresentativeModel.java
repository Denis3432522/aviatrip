package com.example.aviatrip.model.request;

import com.example.aviatrip.model.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RepresentativeModel extends User {

    @JsonProperty("company_name")
    @NotNull
    @Pattern(regexp = "[\\w]{2,64}", message = "must be between 2 and 64 letters")
    private String companyName;

    @JsonProperty("company_name")
    public String getCompanyName() {
        return companyName;
    }
}
