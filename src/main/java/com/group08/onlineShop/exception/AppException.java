package com.group08.onlineShop.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@AllArgsConstructor
public class AppException extends RuntimeException {
    private int code;
    private String message;

}
