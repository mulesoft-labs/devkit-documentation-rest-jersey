package org.mule.examples.restjerseyconnector.exception;

public class RestJerseyConnectorException extends Exception {

	private static final long serialVersionUID = 1L;

	public RestJerseyConnectorException() {
	}

	public RestJerseyConnectorException(String message) {
		super(message);
	}

	public RestJerseyConnectorException(Throwable cause) {
		super(cause);
	}

	public RestJerseyConnectorException(String message, Throwable cause) {
		super(message, cause);
	}

}
