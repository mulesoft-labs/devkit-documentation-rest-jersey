package org.mule.examples.restjerseyconnector.exception;

public class RestJerseyConnectorTokenExpiredException extends Exception {

	private static final long serialVersionUID = 1L;

	public RestJerseyConnectorTokenExpiredException() {
	}

	public RestJerseyConnectorTokenExpiredException(String message) {
		super(message);
	}

	public RestJerseyConnectorTokenExpiredException(Throwable cause) {
		super(cause);
	}

	public RestJerseyConnectorTokenExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

}
