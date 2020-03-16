package com.casa.aems.identity.exception;

public class AppException extends Exception {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/** application specific error code */
	int appCode; 

	Throwable exception;

	/**
	 * 
	 * @param code
	 * @param message
	 */
	public AppException(int code, String message) {
		super(message);
		this.appCode = code;
	}

	/**
	 * 
	 * @param code
	 *
	 */
	public AppException(int code,Throwable ex) {
		this.exception = ex;
		this.appCode = code;
	}

	/**
	 * 
	 * @param code
	 * @param message
	 */
	public AppException(int code, String message,Throwable ex) {
		super(message);
		this.appCode = code;
		this.exception = ex;
	}

	public AppException() { }


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

	/**
	 * @return the exception
	 */
	public Throwable getException() {
		return exception;
	}

	/**
	 * @param exception the exception to set
	 */
	public void setException(Throwable exception) {
		this.exception = exception;
	}

}
