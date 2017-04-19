package br.com.vitrinedecristal.exception;

@SuppressWarnings("serial")
public class InvalidPermissionException extends BusinessException {

	public InvalidPermissionException() {
		super("Permissão inválida");
	}

}