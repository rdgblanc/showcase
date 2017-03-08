package br.com.vitrinedecristal.security.credential;

import java.util.ArrayList;
import java.util.List;

public class Roles {

	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";
	public static final String ROLE_USER = "ROLE_USER";

	public static List<String> getRoles() {
		List<String> roles = new ArrayList<String>();
		roles.add(ROLE_ADMIN);
		roles.add(ROLE_CUSTOMER);
		roles.add(ROLE_USER);
		return roles;
	}

}