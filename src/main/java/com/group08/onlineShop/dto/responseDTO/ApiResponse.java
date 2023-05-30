package com.group08.onlineShop.dto.responseDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@JsonPropertyOrder({
        "success",
        "message",
        "status"
})
public class ApiResponse implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 7702134516418120340L;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty
    private Integer status;

    private Object data;

    public ApiResponse() {
    }

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(Boolean success, String message, Integer httpStatus) {
        this.success = success;
        this.message = message;
        this.status = httpStatus;
    }

    public ApiResponse(Boolean success, String message, Integer status, Object data) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.data = data;
    }
}

