package br.com.vitrinedecristal.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Exceção lançada quando algum campo de uma entidade é inválido.
 */
public class InvalidFieldException extends Exception {

	private static final long serialVersionUID = 6529084023636837350L;

	private List<String> fields = new ArrayList<String>();

	private String message;

	public InvalidFieldException() {
		super();
	}

	public InvalidFieldException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message + (fields.isEmpty() ? "" : StringUtils.join(fields, ", "));
	}

	/**
	 * Indica que um campo é inválido.
	 * 
	 * @param field
	 *            o nome do campo inválido.
	 */
	public void addField(String field) {
		this.fields.add(field);
	}

	/**
	 * Indica se existe um ou mais campos inválidos.
	 * 
	 * @return <tt>true</tt> caso exista e <tt>false</tt> caso contrário.
	 */
	public boolean hasInvalidFields() {
		return !fields.isEmpty();
	}
}
