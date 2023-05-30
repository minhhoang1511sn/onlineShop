package com.group08.onlineShop.exception;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;

public class BlogapiException extends RuntimeException {

    private static final long serialVersionUID = -6593330219878485669L;

    private final Integer status;
    private final String message;

    public BlogapiException(Integer status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public BlogapiException(Integer status, String message, Throwable exception) {
        super(exception);
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
