package br.com.vitrinedecristal.swagger;

@SuppressWarnings("serial")
public class ApiException extends Exception {

	public ApiException(String message) {
		super(message);
	}

}