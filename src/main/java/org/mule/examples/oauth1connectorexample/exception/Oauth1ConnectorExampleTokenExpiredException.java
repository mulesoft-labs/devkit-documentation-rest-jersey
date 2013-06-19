package org.mule.examples.oauth1connectorexample.exception;

public class Oauth1ConnectorExampleTokenExpiredException extends Exception {

	private static final long serialVersionUID = 1L;

	public Oauth1ConnectorExampleTokenExpiredException() {
	}

	public Oauth1ConnectorExampleTokenExpiredException(String message) {
		super(message);
	}

	public Oauth1ConnectorExampleTokenExpiredException(Throwable cause) {
		super(cause);
	}

	public Oauth1ConnectorExampleTokenExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

}
