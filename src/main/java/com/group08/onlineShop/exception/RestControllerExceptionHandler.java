package com.group08.onlineShop.exception;

import com.group08.onlineShop.dto.responseDTO.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestControllerExceptionHandler {
    public ResponseEntity<ApiResponse> resolveException(BlogapiException exception) {
        String message = exception.getMessage();
        Integer status = exception.getStatus();

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setSuccess(Boolean.FALSE);
        apiResponse.setMessage(message);
        apiResponse.setStatus(status);

        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(status));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> resolveException(BadRequestException exception) {
        ApiResponse apiResponse = exception.getApiResponse();

        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> resolveException(ResourceNotFoundException exception) {
        ApiResponse apiResponse = exception.getApiResponse();

        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse> resolveException(UserNotFoundException exception) {
        ApiResponse apiResponse = exception.getApiResponse();
        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }
}
