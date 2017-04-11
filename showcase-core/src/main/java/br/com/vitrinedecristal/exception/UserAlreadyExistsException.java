package br.com.vitrinedecristal.exception;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends BusinessException {

	public UserAlreadyExistsException() {
		super("O email informado já está sendo usado");
	}

}