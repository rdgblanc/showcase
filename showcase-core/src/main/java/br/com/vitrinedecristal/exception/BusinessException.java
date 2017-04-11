package br.com.vitrinedecristal.exception;

/**
 * Exceção de negócios do Vitrine de Cristal.<br>
 * Poderá ser lançada para contrato de comunicação em caso de erros com outras camadas e para a API.
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Construção padrão com uma mensagem de erro.
	 * 
	 * @param message
	 */
	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Exception e) {
		super(e);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException() {
		super();
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

}