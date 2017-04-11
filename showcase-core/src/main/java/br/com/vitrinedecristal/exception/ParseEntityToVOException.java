package br.com.vitrinedecristal.exception;

/**
 * Exceção lançada caso não seja possível converter uma entidade para um VO
 */
public class ParseEntityToVOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParseEntityToVOException(Exception e) {
		super("Não foi possível converter a entidade para VO.", e);
	}
}
