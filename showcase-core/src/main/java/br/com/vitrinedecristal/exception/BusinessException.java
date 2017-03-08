package br.com.vitrinedecristal.exception;

@SuppressWarnings("serial")
public class BusinessException extends Exception {

	public BusinessException(String message) {
		super(message);
	}

}