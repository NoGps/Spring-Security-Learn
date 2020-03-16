package com.casa.aems.identity.exception;

import org.springframework.http.HttpStatus;

public class CasaAuthenticationException extends CasaIdentityServiceException {

    private static final long serialVersionUID = 1L;

    private final HttpStatus status = HttpStatus.UNAUTHORIZED;

    public CasaAuthenticationException(String message) {
        super(message);
    }

    public CasaAuthenticationException(String message, Throwable t) {
        super(message, t);
    }
    
    public HttpStatus getHttpStatus() {
        return status;
    }
}
