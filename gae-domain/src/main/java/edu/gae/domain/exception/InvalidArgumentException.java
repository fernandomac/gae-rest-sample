package edu.gae.domain.exception;

public class InvalidArgumentException extends RuntimeException {

	private static final long serialVersionUID = 12L;

	public InvalidArgumentException(String message) {
		super(message);
	}
}
