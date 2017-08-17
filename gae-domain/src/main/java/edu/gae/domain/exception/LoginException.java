package edu.gae.domain.exception;

public class LoginException extends RuntimeException{

	private static final long serialVersionUID = 11L;
	private LoginError error;

	public LoginException(LoginError error, String message) {
		super(message);
		this.error = error;
	}

	public LoginError getError() {
		return error;
	}

	public void setError(LoginError error) {
		this.error = error;
	}
}