package com.meva.finance.response;

import org.springframework.validation.FieldError;

public record ValidationErrorResponse(String field, String message) {

    public ValidationErrorResponse(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}