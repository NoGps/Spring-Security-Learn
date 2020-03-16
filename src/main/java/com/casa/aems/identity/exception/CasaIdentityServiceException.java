package com.casa.aems.identity.exception;

import org.springframework.http.HttpStatus;

public abstract class CasaIdentityServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    public CasaIdentityServiceException(String message) {
        super(message);
    }
    
    public CasaIdentityServiceException(String message, Throwable cause) {
        super(message, cause);
    }


    public abstract HttpStatus getHttpStatus();
}
