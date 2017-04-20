package br.com.vitrinedecristal.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import br.com.vitrinedecristal.exception.InvalidFieldException;
import br.com.vitrinedecristal.exception.ValidationException;

/**
 * Classe utilitária para validações.
 */
public class ValidatorUtil {

	private static final Logger logger = Logger.getLogger(ValidatorUtil.class);

	/**
	 * Valida se o offset e o size são válidos
	 * 
	 * @param offset início da consulta
	 * @param size tamanho da consulta
	 */
	public static void validateOffsetAndSize(Integer offset, Integer size) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset não pode ser menor que 0");
		}

		if (size < 1) {
			throw new IllegalArgumentException("Size não pode ser menor que 1");
		}
	}

	/**
	 * Valida se o parâmetro não é nulo.
	 * 
	 * @param object o objeto a ser validado.
	 * @param message a mensagem da exceção, caso o objeto seja nulo.
	 * @throws IllegalArgumentException caso o objeto seja nulo.
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Valida se o parâmetro não é nulo ou vazio.
	 * 
	 * @param object o objeto a ser validado.
	 * @param message a mensagem da exceção, caso o objeto seja nulo ou vazio.
	 * @throws IllegalArgumentException caso o objeto seja nulo ou vazio.
	 */
	public static void notNull(String object, String message) {
		if (StringUtils.isBlank(object)) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Realiza a validação da entidade de entrada, que não deve ser nula
	 * 
	 * @param t Entidade
	 */
	public static void validate(Object t) {
		if (t == null) {
			throw new IllegalArgumentException("O objeto informado não pode ser nulo");
		}
	}

	/**
	 * Primeiro valida se o objeto passado por parâmetro não é <code>null</code>. Em seguida valida se os campos informados no parâmetro <code>requiredFields</code> não são <code>null</code> ou vazio.
	 * 
	 * @param object o objeto a ser validado.
	 * @param requiredFields o nome dos campos que serão validados no objeto. Se <code>null</code> então nenhum campo será validado.
	 * @throws IllegalArgumentException caso o objeto a ser validado seja <code>null</code>.
	 * @throws InvalidFieldException caso exista um ou mais campos inválidos.
	 * @throws ValidationException caso não seja possível validar o objeto.
	 */
	public static void notNullFields(Object object, String... requiredFields) throws InvalidFieldException, ValidationException {
		if (logger.isDebugEnabled()) {
			logger.debug("Validando os campos " + ToStringBuilder.reflectionToString(requiredFields) + " de " + ToStringBuilder.reflectionToString(object));
		}

		InvalidFieldException exception = new InvalidFieldException("Campo(s) obrigatório(s) inválido(s): ");

		if (requiredFields == null) {
			return;
		}

		if (object == null) {
			throw new IllegalArgumentException("A entidade não pode ser nula.");
		}

		for (String field : requiredFields) {
			try {
				Object value = PropertyUtils.getSimpleProperty(object, field);

				if (value == null || (value.getClass().equals(String.class) && StringUtils.isBlank(value.toString())) || (value instanceof List && ((List<?>) value).isEmpty()) || (value instanceof Map && ((Map<?, ?>) value).isEmpty())) {
					exception.addField(field);
				}
			} catch (IllegalAccessException e) {
				throw new ValidationException(object, field, e);
			} catch (InvocationTargetException e) {
				throw new ValidationException(object, field, e);
			} catch (NoSuchMethodException e) {
				throw new ValidationException(object, field, e);
			}
		}

		if (exception.hasInvalidFields()) {
			throw exception;
		}
	}
}
