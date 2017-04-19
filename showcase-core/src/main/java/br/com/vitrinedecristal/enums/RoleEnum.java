package br.com.vitrinedecristal.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Enum para mapeamento de Role disponíveis para acesso na API.
 */
public enum RoleEnum {

	/**
	 * Perfil admin
	 */
	ROLE_ADMIN,

	/**
	 * Perfil para usuário
	 */
	ROLE_USER;

	public static boolean containsAll(List<RoleEnum> roles) {
		List<RoleEnum> list = Arrays.asList(RoleEnum.values());
		return list.containsAll(roles);
	}

}
