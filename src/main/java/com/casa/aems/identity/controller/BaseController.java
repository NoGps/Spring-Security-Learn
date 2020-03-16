package com.casa.aems.identity.controller;

import com.casa.aems.identity.exception.ApiErrorInfo;
import com.casa.aems.identity.exception.AppException;
import com.casa.aems.identity.exception.EntityNotFoundException;
import com.casa.aems.identity.exception.ErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Principal;

@Controller
public class BaseController {
    final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<String> handleUncheckedExceptions(Throwable ex,
                                                            HttpServletRequest request) {
        log.error("handleUncheckedExceptions", ex);

        ApiErrorInfo info = new ApiErrorInfo();
        info.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        if (ex instanceof AccessDeniedException) {
            info.setStatus(HttpStatus.FORBIDDEN);
        }
        info.setAppCode(ErrorCodes.GENERAL_ERROR);

        info.setMessage(String.format("Error processing request, reason %s",
                ex.getMessage()));

        StringWriter errorStackTrace = new StringWriter();
        ex.printStackTrace(new PrintWriter(errorStackTrace));

        return new ResponseEntity<String>(info.toJson(), info.getStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ResponseEntity<String> handleBadCredentialsExceptions(Throwable ex,
                                                                 HttpServletRequest request, Principal p) {

        log.error("handleAppExceptions", ex);

        ApiErrorInfo info = new ApiErrorInfo();
        info.setAppCode(ErrorCodes.AUTHENTICATION_ERROR);
        info.setMessage(ex.getMessage());
        info.setStatus(HttpStatus.FORBIDDEN);

        return new ResponseEntity<String>(info.toJson(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<String> handleAppExceptions(AppException ex,
                                                      HttpServletRequest request, Principal p) {

        log.error("handleAppExceptions", ex);

        ApiErrorInfo info = new ApiErrorInfo(ex);

        return new ResponseEntity<String>(info.toJson(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleEntityNotFoundExceptions(
            EntityNotFoundException ex, HttpServletRequest request,
            Principal p) {

        log.error("handleEntityNotFoundExceptions", ex);

        ApiErrorInfo info = new ApiErrorInfo(ex);

        return new ResponseEntity<String>(info.toJson(), HttpStatus.NOT_FOUND);
    }
}
