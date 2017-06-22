package br.com.vitrinedecristal.enums;

import br.com.vitrinedecristal.model.Token;

/**
 * Tipos de {@link Token token}
 * 
 * @see Token#getTipo()
 */
public enum TokenEnum {

	/** Cadastro de um usuário */
	WELCOME,

	/** Login de um usuário */
	AUTHENTICATION,

	/** Recuperação de senha de um usuário */
	FORGOT_PASSWORD,

	/** Alteração de senha de um usuário */
	CHANGE_PASSWORD;

}
