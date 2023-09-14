package com.example.aviatrip.config.requestmodel;

import com.example.aviatrip.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RepresentativeModel extends User {

    @JsonProperty("company_name")
    @NotNull
    @Pattern(regexp = "[\\w]{2,64}")
    private String companyName;

    @JsonProperty("company_name")
    public String getCompanyName() {
        return companyName;
    }
}
