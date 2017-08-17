package edu.gae.domain.exception;

public class NotFoundException extends RuntimeException{

	private static final long serialVersionUID = 11L;
	private String objectName;

	public NotFoundException(String message, String objectName) {
		super(message);
		this.objectName = objectName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}	
	
}
