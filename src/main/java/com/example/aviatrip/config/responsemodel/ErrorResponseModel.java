package com.example.aviatrip.config.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponseModel {

    @JsonProperty("error_msg")
    private String errorMsg;

    public ErrorResponseModel(String message) {
        errorMsg= message;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
