package com.casa.aems.identity.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class ApiErrorInfo {
	
	private HttpStatus status;
	
	private int appCode;
	
	private String message;
	
	private HashMap<String,String> extraInfo;

	public HttpStatus getStatus() {
		return status;
	}
	
	public ApiErrorInfo(AppException ex){
		appCode = ex.getAppCode();
		message = ex.getMessage();
	}

	public ApiErrorInfo(EntityNotFoundException ex){	
		appCode = ex.getAppCode();
		message = ex.getMessage();
		extraInfo = new HashMap<String,String>();
		extraInfo.put("entityId", ex.getEntityId());
		extraInfo.put("entityType", ex.getEntityType());		
	}

	public ApiErrorInfo(){};	

	public void setStatus(HttpStatus internalServerError) {
		this.status = internalServerError;
	}

    public String toJson() {
        ObjectMapper m = new ObjectMapper();

        try {
            return (m.writerWithDefaultPrettyPrinter().writeValueAsString(this));
        }
        catch (Exception e) {
            return "{ ERROR }";
        }
    }

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the appCode
	 */
	public int getAppCode() {
		return appCode;
	}

	/**
	 * @param appCode the appCode to set
	 */
	public void setAppCode(int appCode) {
		this.appCode = appCode;
	}
}
