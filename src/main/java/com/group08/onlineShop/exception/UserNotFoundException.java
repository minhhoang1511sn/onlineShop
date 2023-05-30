package com.group08.onlineShop.exception;

import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    private ApiResponse apiResponse;

    private String email;

    public UserNotFoundException(String email) {
        super();
        this.email = email;
        setApiResponse();
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }

    private void setApiResponse() {
        String message = String.format("Account not found with email: '%s'",email);
        apiResponse = new ApiResponse(Boolean.FALSE, message, HttpStatus.NOT_FOUND.value(), null);
    }
}