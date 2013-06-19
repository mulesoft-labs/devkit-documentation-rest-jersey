package org.mule.examples.oauth1connectorexample.exception;

public class Oauth1ConnectorExampleException extends Exception {

	private static final long serialVersionUID = 1L;

	public Oauth1ConnectorExampleException() {
	}

	public Oauth1ConnectorExampleException(String message) {
		super(message);
	}

	public Oauth1ConnectorExampleException(Throwable cause) {
		super(cause);
	}

	public Oauth1ConnectorExampleException(String message, Throwable cause) {
		super(message, cause);
	}

}
