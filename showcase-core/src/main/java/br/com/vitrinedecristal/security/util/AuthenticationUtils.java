package br.com.vitrinedecristal.security.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.vitrinedecristal.security.credential.UserCredentials;

public class AuthenticationUtils {

	public static void login(Authentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public static void logout() {
		SecurityContextHolder.clearContext();
	}

	public static Long getUserId() {
		return getUserCredentials().getId();
	}

	public static UserCredentials getUserCredentials() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() instanceof UserCredentials) {
			UserCredentials userCredentials = (UserCredentials) auth.getPrincipal();
			return userCredentials;
		}

		throw new IllegalArgumentException("Usuario não encontrado");
	}

	public static List<String> listUserRoles() {
		List<String> roles = new ArrayList<String>();

		UserCredentials userCredentials = getUserCredentials();
		if (userCredentials != null) {
			for (GrantedAuthority authority : userCredentials.getAuthorities()) {
				roles.add(authority.getAuthority());
			}
		}

		return roles;
	}

}