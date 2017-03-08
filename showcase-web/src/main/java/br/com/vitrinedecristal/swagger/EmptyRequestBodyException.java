package br.com.vitrinedecristal.swagger;

@SuppressWarnings("serial")
public class EmptyRequestBodyException extends BadRequestException {

	public static final String MESSAGE = "The request could not be completed due to empty body.";

	public EmptyRequestBodyException() {
		super(MESSAGE);
	}

}