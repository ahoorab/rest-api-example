package com.example.ex.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntityReferenceException extends RuntimeException {

    private static final long serialVersionUID = 3762308923521377548L;

    public EntityReferenceException() {
        super();
    }

    public EntityReferenceException(String message) {
        super(message);
    }

}
