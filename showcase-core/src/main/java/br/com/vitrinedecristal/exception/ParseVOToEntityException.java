package br.com.vitrinedecristal.exception;

/**
 * Exceção lançada caso não seja possível converter um VO para um entidade
 */
public class ParseVOToEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParseVOToEntityException(Exception e) {
		super("Não foi possível converter o VO para entidade.", e);
	}
}
