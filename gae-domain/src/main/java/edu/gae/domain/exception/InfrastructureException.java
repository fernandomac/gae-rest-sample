package edu.gae.domain.exception;

public class InfrastructureException extends RuntimeException {

	private static final long serialVersionUID = -8472880391514077340L;
	
	public InfrastructureException(String message, Throwable e){
		super(message, e);
	}
	
}
