package br.com.vitrinedecristal.exception;

public class UserLoginException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public UserLoginException() {
		super();
	}

	public UserLoginException(Exception e) {
		super(e);
	}

	public UserLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserLoginException(String message) {
		super(message);
	}

	public UserLoginException(Throwable cause) {
		super(cause);
	}

}
