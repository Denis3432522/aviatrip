package com.example.aviatrip.config.responsebody;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponseBody {

    @JsonProperty("error_msg")
    private String errorMsg;

    public ErrorResponseBody(String message) {
        errorMsg= message;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
