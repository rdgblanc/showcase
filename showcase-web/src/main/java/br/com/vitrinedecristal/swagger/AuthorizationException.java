package br.com.vitrinedecristal.swagger;

@SuppressWarnings("serial")
public class AuthorizationException extends ApiException {

	public static final String MESSAGE = "The request could not be completed due to invalid authorization.";

	public AuthorizationException() {
		super(MESSAGE);
	}

}