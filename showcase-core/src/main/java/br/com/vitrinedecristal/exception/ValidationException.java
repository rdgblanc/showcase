package br.com.vitrinedecristal.exception;

/**
 * Exceção lançada caso ocorra erro em alguma validação.
 */
public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ValidationException() {
		super();
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

	public ValidationException(Object bean, String field, Throwable cause) {
		super("Não foi possível validar o campo '" + field + "' de " + bean, cause);
	}

}
