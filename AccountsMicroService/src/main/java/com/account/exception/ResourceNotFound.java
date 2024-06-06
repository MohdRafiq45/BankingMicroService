package com.account.exception;

public class ResourceNotFound extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFound() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResourceNotFound(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	public ResourceNotFound(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with the given input data %s : '%s'", resourceName, fieldName, fieldValue));
    }
	

}
