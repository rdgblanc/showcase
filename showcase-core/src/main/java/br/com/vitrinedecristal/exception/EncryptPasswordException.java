package br.com.vitrinedecristal.exception;

/**
 * Exceção lançada caso não seja possível criptografar a senha do usuário
 */
public class EncryptPasswordException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public EncryptPasswordException(Throwable cause) {
		super("Não foi possível criptografar a senha.", cause);
	}

}
