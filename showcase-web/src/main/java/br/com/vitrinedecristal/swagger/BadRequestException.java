package br.com.vitrinedecristal.swagger;

@SuppressWarnings("serial")
public class BadRequestException extends ApiException {

	public static final String MESSAGE = "The request could not be completed due to incorrect syntax.";

	public BadRequestException(String message) {
		super(message);
	}

}